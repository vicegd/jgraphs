package jgraphs.algorithm.mcts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Properties;

import com.google.inject.Inject;

import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.defaultpolicy.IDefaultPolicy;
import jgraphs.algorithm.mcts.treepolicy.ITreePolicy;
import jgraphs.core.node.INode;
import jgraphs.core.process.AbstractProcess;
import jgraphs.core.structure.ITree;
import jgraphs.core.structure.Tree;
import jgraphs.core.utils.IllegalTreeOperationException;
import jgraphs.core.utils.Utils;

public class MCTS extends AbstractProcess {
	private ITreePolicy treePolicy;
	private IDefaultPolicy defaultPolicy;
	private IBudgetManager budgetManager;
	private boolean[] trainers;
    
	@Inject
    public MCTS(ITree tree, ITreePolicy treePolicy, IDefaultPolicy defaultPolicy,  IBudgetManager budgetManager) {
		super.structure = tree;
		this.treePolicy = treePolicy;
		this.defaultPolicy = defaultPolicy;
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
        } catch (IOException ex) {
       		log.error(ex.getMessage());
        }
    }
	
	public INode execute(INode node) {
    	super.totalTimer = Instant.now();   	   	
    	var winnerNode = this.mcts(node);   	
    	return winnerNode;
	}
              
    private INode mcts(INode node) {     
    	for (var i = 1; i < Integer.MAX_VALUE; i++) {
    		super.processTimer = Instant.now(); 
        	
            // Phase 1 - Selection
            var promisingNode = selection(node);
            
            // Phase 2 - Expansion
            if ((promisingNode.getState().getVisitCount() >= 1)||(promisingNode.equals(super.structure.getFirst())))
            	expansion(promisingNode); //Only expand it if it is the root node or it has already been visited yet

            // Phase 3 - Simulation
            var nodeToExplore = promisingNode;
            if (promisingNode.getSuccessors().size() > 0) {
                nodeToExplore = promisingNode.getRandomSuccessorNode();
            }
            var result = simulation(nodeToExplore);
            
            // Phase 4 - Update
            backPropogation(nodeToExplore, result);

            super.incrementProcessDuration(super.processTimer);      
            super.structureChangedEvent(super.structure, node, nodeToExplore, this.movementNumber, i, result);
            if (budgetManager.checkStopCondition(i, super.totalTimer)) break; 
        }

        var winnerNode = node.getSuccessorWithMaxValue(node.getState().getParticipantManager().getOpponent());
        
        super.movementPerformedEvent(super.structure, node, winnerNode, super.movementNumber);       
        super.movementNumber++;
        super.incrementTotalDuration(super.totalTimer);
        if (winnerNode.getState().getSituation().checkStatus() != -1) {
        	super.result.add(winnerNode); 		       	
        	super.processFinishedEvent(super.structure, super.result, super.processDuration, super.totalDuration);
        }
        
        return winnerNode;
    }
    
    public IBudgetManager getBudgetManager() {
    	return this.budgetManager;
    }
    
    public void setTrainers(boolean[] trainers) {
    	this.trainers = trainers;
    }

    private INode selection(INode rootNode) {
        var node = rootNode;

        while (node.getSuccessors().size() != 0) {
            node = treePolicy.findBestNode(rootNode.getState().getParticipantManager().getOpponent(), node);
        }
        return node;
    }

    private void expansion(INode promisingNode) {
    	var possibleStates = promisingNode.getState().nextStates();
	    possibleStates.forEach(state -> {
	    	var newNode = Utils.getInstance().getInjector().getInstance(INode.class);
	    	newNode.setState(state);
	    	newNode.getPredecessors().add(promisingNode);
	        promisingNode.getSuccessors().add(newNode);     
			try {
				var tree = (Tree)super.structure;
				tree.addNode(newNode);
			} catch (IllegalTreeOperationException e) {
				log.error(e.getMessage());
			}
	    });
    }

    private int simulation(INode node) {
    	return defaultPolicy.simulation(node);
    }
    
    private void backPropogation(INode nodeToExplore, int result) {
    	var node = nodeToExplore;
    	var numberOfPlayers = nodeToExplore.getState().getParticipantManager().getNumberOfParticipants();
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
        	if (node.getPredecessors().size() > 0)
        		node = node.getPredecessors().get(0);          	
        	else node = null;
       }
    }
}
