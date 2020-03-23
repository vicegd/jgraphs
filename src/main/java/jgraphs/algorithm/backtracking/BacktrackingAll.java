package jgraphs.algorithm.backtracking;

import com.google.inject.Inject;

import jgraphs.algorithm.manager.AbstractManager;
import jgraphs.core.node.INode;
import jgraphs.core.structure.tree.ITree;
import jgraphs.utils.Dependency;

public class BacktrackingAll extends AbstractManager {  
	@Inject
    public BacktrackingAll(ITree tree) {
		super();
		super.setStructure(tree);
    }
	
	@Override
	public void run(INode node) {
    	this.backtracking(node);  
    	super.checkpointEvent();
    	super.processFinishedEvent();
	}
	   
    private void backtracking(INode node) { 
    	super.incrementMovementNumber();
    	node.getState().incrementVisit();
    	
    	super.pauseEvent();
    	
    	if (node.getState().getSituation().hasFinished()) {
    		//super.movementPerformedEvent(super.getStructure(), super.getStructure().getSecondToLast(), super.getStructure().getLast(), super.getMovementNumber());       
    		super.addResult(node);
    	}
    	else {
        	var possibleStates = node.getState().nextStates();
    	    possibleStates.forEach(state -> {
    	    	var newNode = Dependency.getInstance().createNodeInstance();
    	    	newNode.setState(state);
    	    	newNode.getPredecessors().add(node);
    	        node.getSuccessors().add(newNode);   
    	        super.addNodeToTreeStructure(newNode);
    	        super.movementPerformedEvent(node, newNode); 
    	        super.iterationPerformedEvent(node, newNode, 0); 
    			this.backtracking(newNode);
    	    });
    	}
    }
}