package jgraphs.algorithm.backtracking;

import java.time.Duration;
import java.time.Instant;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.process.AbstractProcess;
import jgraphs.core.structure.ITree;
import jgraphs.core.structure.Tree;
import jgraphs.core.utils.IllegalTreeOperationException;
import jgraphs.core.utils.Utils;

public class BacktrackingAll extends AbstractProcess {    
	@Inject
    public BacktrackingAll(ITree tree) {
		super.structure = tree;
    }
	
	@Override
	protected void executeAlgorithm(INode node) {
		this.backtracking(node);	
    	super.processDuration = super.processDuration.plus(Duration.between(super.processTimer, Instant.now()));   
	}
	   
    private void backtracking(INode node) { 
    	super.movementNumber++;
    	node.getState().incrementVisit();
    	
    	if (node.getState().getSituation().checkStatus() != -1) {
    		super.movementPerformedEvent(super.structure, super.structure.getFirst(), super.structure.getLast(), this.movementNumber);       
    		super.result.add(node);
    	}
    	else {
        	var possibleStates = node.getState().nextStates();
    	    possibleStates.forEach(state -> {
    	    	var newNode = Utils.getInstance().getInjector().getInstance(INode.class);

    	    	newNode.setState(state);
        	
    	    	newNode.getPredecessors().add(node);
    	        node.getSuccessors().add(newNode);     
    			try {
    				var tree = (Tree)super.structure;
    				tree.addNode(newNode);
    			} catch (IllegalTreeOperationException e) {
    				log.error(e.getMessage());
    			}
    			this.backtracking(newNode);
    	    });
    	}
    }
    
}
