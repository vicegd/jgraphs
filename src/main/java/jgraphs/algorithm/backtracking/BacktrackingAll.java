package jgraphs.algorithm.backtracking;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.process.AbstractProcess;
import jgraphs.core.structure.ITree;
import jgraphs.core.utils.Utils;

public class BacktrackingAll extends AbstractProcess {    
	@Inject
    public BacktrackingAll(ITree tree) {
		super.setStructure(tree);
    }
	
	@Override
	public void run(INode node) {
    	this.backtracking(node);   	
	}
	   
    private void backtracking(INode node) { 
    	super.incrementMovementNumber();
    	node.getState().incrementVisit();
    	
    	if (node.getState().getSituation().hasFinished()) {
    		super.movementPerformedEvent(super.getStructure(), super.getStructure().getSecondToLast(), super.getStructure().getLast(), super.getMovementNumber());       
    		super.addResult(node);
    	}
    	else {
        	var possibleStates = node.getState().nextStates();
    	    possibleStates.forEach(state -> {
    	    	var newNode = Utils.getInstance().createNodeInstance();
    	    	newNode.setState(state);
    	    	newNode.getPredecessors().add(node);
    	        node.getSuccessors().add(newNode);     
    	        super.addNodeToTreeStructure(newNode);
    			this.backtracking(newNode);
    	    });
    	}
    }
}