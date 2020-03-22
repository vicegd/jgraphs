package jgraphs.algorithm.backtracking;

import com.google.inject.Inject;

import jgraphs.algorithm.manager.AbstractManager;
import jgraphs.core.node.INode;
import jgraphs.core.structure.tree.ITree;
import jgraphs.utils.Dependency;

public class BacktrackingOne extends AbstractManager {    
	private boolean end;
	
	@Inject
    public BacktrackingOne(ITree tree) {
		super();
		super.setStructure(tree);
		this.end = false;
    }
	
	@Override
	public void run(INode node) {
    	this.backtracking(node);  
    	super.checkpointEvent();
	}
	   
    private void backtracking(INode node) { 
    	super.incrementMovementNumber();
    	node.getState().incrementVisit();
    	
    	super.pauseEvent();
    	
    	if (node.getState().getSituation().hasFinished()) {
    		//super.movementPerformedEvent(super.getStructure(), super.getStructure().getSecondToLast(), super.getStructure().getLast(), super.getMovementNumber());        
    		super.addResult(node);
    		this.end = true;
    	}
    	else if (!this.end) {
        	var possibleStates = node.getState().nextStates();
    	    possibleStates.forEach(state -> {
    	    	if (!this.end) {
    	    		var newNode = Dependency.getInstance().createNodeInstance();	
	    	    	newNode.setState(state); 	
	    	    	newNode.getPredecessors().add(node);
	    	        node.getSuccessors().add(newNode);     
	    	        super.addNodeToTreeStructure(newNode);
	    			this.backtracking(newNode);
    	    	}
    	    });
    	}
    }
}