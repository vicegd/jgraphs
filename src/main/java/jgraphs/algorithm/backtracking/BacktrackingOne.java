package jgraphs.algorithm.backtracking;

import java.time.Instant;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.process.AbstractProcess;
import jgraphs.core.structure.ITree;
import jgraphs.core.structure.Tree;
import jgraphs.core.utils.IllegalTreeOperationException;
import jgraphs.core.utils.Utils;

public class BacktrackingOne extends AbstractProcess {    
	private boolean end;
	
	@Inject
    public BacktrackingOne(ITree tree) {
		super.structure = tree;
		this.end = false;
    }
	
	public void execute(INode node) {
    	super.totalTimer = Instant.now();
    	super.processTimer = Instant.now();
    	
    	this.backtracking(node);   	
		
    	super.incrementProcessDuration(super.processTimer); 
    	super.incrementTotalDuration(super.totalTimer);
    	super.processFinishedEvent(super.structure, super.result, this.processDuration, this.totalDuration);
	}
	   
    private void backtracking(INode node) { 
    	super.movementNumber++;
    	node.getState().incrementVisit();
    	
    	if (node.getState().getSituation().hasFinished()) {
    		var a = Instant.now();
    		super.movementPerformedEvent(super.structure, super.structure.getSecondToLast(), super.structure.getLast(), this.movementNumber);        
    		super.decrementProcessDuration(a);
    		super.result.add(node);
    		this.end = true;
    	}
    	else if (!this.end) {
        	var possibleStates = node.getState().nextStates();
    	    possibleStates.forEach(state -> {
    	    	if (!this.end) {
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
    	    	}
    	    });
    	}
    }
    
}
