package jgraphs.algorithm.mcts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.defaultpolicy.IDefaultPolicy;
import jgraphs.algorithm.mcts.treepolicy.ITreePolicy;
import jgraphs.core.node.INode;
import jgraphs.core.process.AbstractProcess;
import jgraphs.core.tree.ITree;
import jgraphs.core.utils.Utils;
import jgraphs.statistics.IStatistic;

public class MCTS extends AbstractProcess {
	private static Logger log = LoggerFactory.getLogger(MCTS.class);
	private ITree tree;
	private ITreePolicy treePolicy;
	private IDefaultPolicy defaultPolicy;
	private IStatistic statistics;
	private IBudgetManager budgetManager;
	private int movementNumber;
	private boolean[] trainers;
	private boolean addInfoToTree;
    
	@Inject
    public MCTS(ITree tree, ITreePolicy treePolicy, IDefaultPolicy defaultPolicy, IStatistic statistics, IBudgetManager budgetManager) {
		this.tree = tree;
		this.treePolicy = treePolicy;
		this.defaultPolicy = defaultPolicy;
		this.statistics = statistics;
		this.budgetManager = budgetManager;
    	this.movementNumber = 1;
    	try (InputStream input = new FileInputStream("src/main/java/config.properties")) {
            var prop = new Properties();
            prop.load(input);
            var trainersValue = prop.getProperty("mcts.trainers").split(" ");
            this.trainers = new boolean[trainersValue.length];
            for (var i = 0; i < trainersValue.length; i++) {
            	this.trainers[i] = Boolean.parseBoolean(trainersValue[i]);
            }
            this.addInfoToTree = Boolean.parseBoolean(prop.getProperty("mcts.add_info_to_tree"));
        } catch (IOException ex) {
       		log.error(ex.getMessage());
        }
    }
	
	public ITree getTree() {
		return this.tree;
	}
	
	public IStatistic getStatistics() {
		return this.statistics;
	}
	
	public IBudgetManager getBudgetManager() {
		return this.budgetManager;
	}
           
    public void setTrainers(boolean[] trainers) {
    	this.trainers = trainers;
    }
	   
    public INode findNextMove(INode node) {     
    	var startTimer = Instant.now();
    	for (var i = 1; i < Integer.MAX_VALUE; i++) {
        	var processTimer = Instant.now();
        	
            // Phase 1 - Selection
            var promisingNode = selection(node);
            
            // Phase 2 - Expansion
            if ((promisingNode.getState().getVisitCount() >= 1)||(promisingNode.equals(this.tree.getRoot())))
            	expansion(promisingNode); //Only expand it if it is the root node or it has already been visited yet

            // Phase 3 - Simulation
            var nodeToExplore = promisingNode;
            if (promisingNode.getChildArray().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            var result = simulation(nodeToExplore);
            
            // Phase 4 - Update
            backPropogation(nodeToExplore, result);

            this.processDuration = processDuration.plus(Duration.between(processTimer, Instant.now()));          
            super.treeChangedEvent(this.tree, node, nodeToExplore, result, this.movementNumber, i);
            if (budgetManager.checkStopCondition(i, startTimer)) break; 
        }

        var winnerNode = node.getChildWithMaxValue(node.getState().getPlayerManager().getOpponent());
        super.movementPerformedEvent(tree, node, winnerNode, this.movementNumber);
        this.totalDuration = totalDuration.plus(Duration.between(startTimer, Instant.now()));
        if (winnerNode.getState().getBoard().checkStatus() != -1) {
        	super.processFinishedEvent(tree, winnerNode, this.processDuration, this.totalDuration);
        }
        this.movementNumber++;
        return winnerNode;
    }

     
    private INode selection(INode rootNode) {
        var node = rootNode;

        while (node.getChildArray().size() != 0) {
            node = treePolicy.findBestNode(rootNode.getState().getPlayerManager().getOpponent(), node);
        }
        return node;
    }

    private void expansion(INode promisingNode) {
    	var possibleStates = promisingNode.getState().getAllPossibleStates();
	    possibleStates.forEach(state -> {
	    	var newNode = Utils.getInstance().getInjector().getInstance(INode.class);
	    	newNode.setState(state);
	    	
	    	newNode.setParent(promisingNode);
	        promisingNode.getChildArray().add(newNode);     
	        if (addInfoToTree) tree.addNode(newNode);
	    });
    }

    private int simulation(INode node) {
    	return defaultPolicy.simulation(node);
    }
    
    private void backPropogation(INode nodeToExplore, int result) {
    	var node = nodeToExplore;
    	var numberOfPlayers = nodeToExplore.getState().getPlayerManager().getNumberOfPlayers();
        while (node != null) {
        	node.getState().incrementVisit();
        	for (var i = 1; i <= numberOfPlayers; i++) { //check all the players
        		if (this.trainers[i-1] == true) { //we update data only if we should train that player
        			if (result == i) //current player wins
        				node.getState().addScore(i, treePolicy.getWinScore());
        			else if (result == 0) //current player draws
        				node.getState().addScore(i, treePolicy.getDrawScore());
        			else //current player loses - when any other player wins 
        				node.getState().addScore(i, treePolicy.getLoseScore());
        		}
        	}
            node = node.getParent();         	
       }
    }
}
