package jgraphs.algorithm.mcts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import jgraphs.algorithm.mcts.defaultpolicy.IDefaultPolicy;
import jgraphs.algorithm.mcts.treepolicy.ITreePolicy;
import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;
import jgraphs.core.utils.Utils;
import jgraphs.visualizers.IVisualizer;

public class MCTS {
	private static Logger log = LoggerFactory.getLogger(MCTS.class);
	private ITree tree;
	private ITreePolicy treePolicy;
	private IDefaultPolicy defaultPolicy;
	private List<IVisualizer> visualizers;
	private int movementNumber;
	private long iterations;
	private long memory;
	private long seconds;

	private boolean trainP1; //QUITAR*******??
	private boolean trainP2; //QUITAR*******??
	
	private int winScore; //QUITAR*******??
    
	@Inject
    public MCTS(ITree tree, ITreePolicy treePolicy, IDefaultPolicy defaultPolicy) {
		this.tree = tree;
		this.treePolicy = treePolicy;
		this.defaultPolicy = defaultPolicy;
    	this.visualizers = new ArrayList<IVisualizer>();
    	this.movementNumber = 1;
    	try (InputStream input = new FileInputStream("src/main/java/config.properties")) {
            var prop = new Properties();
            prop.load(input);
            this.iterations = Long.parseLong(prop.getProperty("mcts.budget_iterations"));
            this.memory = Long.parseLong(prop.getProperty("mcts.budget_memory"));
            this.seconds = Long.parseLong(prop.getProperty("mcts.budget_seconds"));
            this.winScore = Integer.parseInt(prop.getProperty("mcts.win_score"));
            this.trainP1 = Boolean.parseBoolean(prop.getProperty("mcts.train_p1"));
            this.trainP2 = Boolean.parseBoolean(prop.getProperty("mcts.train_p2"));
       } catch (IOException ex) {
       		log.error(ex.getMessage());
       }
    }
	
	public ITree getTree() {
		return this.tree;
	}
           
    public void setTrainP1(boolean train) {
    	this.trainP1 = train;
    }
    
    public void setTrainP2(boolean train) {
    	this.trainP2 = train;
    }

    public void setIterations(int iterations) {
    	this.iterations = iterations;
    }
    
    public void setMemory(int memory) {
    	this.memory = memory;
    }
    
    public void setSeconds(int seconds) {
    	this.seconds = seconds;
    }
    
    public void setWinScore(int winScore) {
    	this.setWinScore(winScore);
    }
    
    public void addVisualizer(IVisualizer visualizer) {
    	visualizers.add(visualizer);
    }
	   
    public INode findNextMove(INode node) {     
    	var start = Instant.now();
    	for (var i = 1; i < Integer.MAX_VALUE; i++) {
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
            /*var score = 0;
            if (this.trainP1) {
            	if (result == 1) score = this.winScore;
            	else if (result == 2) score = -this.winScore;
            }
            if (this.trainP2) {
            	if (result == 1) score = -this.winScore;
            	else if (result == 2) score = this.winScore;
            }*/
            backPropogation(nodeToExplore, result);

            this.treeChangedEvent(node, nodeToExplore, result, i);
            if (checkStopCondition(i, start)) break; 
        }

        var winnerNode = node.getChildWithMaxValue((node.getState().getPlayerManager().getPlayer()!=0)?node.getState().getPlayerManager().getPlayer():1);
        this.movementPerformedEvent(winnerNode);
        this.movementNumber++;
        return winnerNode;
    }
 
    private void movementPerformedEvent(INode winnerNode) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.movementPerformedEvent(winnerNode);
    	}
    }
    
    private void treeChangedEvent(INode currentNode, INode nodeToExplore, int result, int iterationNumber) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.treeChanged(this.tree, currentNode, nodeToExplore, result, this.movementNumber, iterationNumber);
    	}
    }
    
    private boolean checkStopCondition(int iterationNumber, Instant start) {
        var memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (memoryUsed >= this.memory) return true; //break because of memory
               
        var duration = Duration.between(start, Instant.now());       
        if (duration.getSeconds() >= this.seconds) return true; //break because of seconds
        
        if (iterationNumber >= this.iterations) return true; //break because of iterations
        return false;
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
	        tree.addNode(newNode);
	    });
    }

    private int simulation(INode node) {
    	return defaultPolicy.simulation(node);
    }
    
    private void backPropogation(INode nodeToExplore, int result) {
    	var score = 2.0;
    	if (result == 0)
    		score = 0;
    	var node = nodeToExplore;
    	var numberOfPlayers = nodeToExplore.getState().getPlayerManager().getNumberOfPlayers();
        while (node != null) {
        	node.getState().incrementVisit();
        	for (var i = 2; i <= numberOfPlayers; i++) {
        		if (node.getState().getPlayerManager().getPlayer() == i) {
        			if (node.getState().getPlayerManager().getPlayer() == result)
        				node.getState().addScore(i, score);
        			else
        				node.getState().addScore(i, -score);
        		}
        		else {
        			if (node.getState().getPlayerManager().getPlayer() == result)
        				node.getState().addScore(i, -score);
        			else
        				node.getState().addScore(i, score);
        		}
        	}
            node = node.getParent();         	
       }
    }
}
