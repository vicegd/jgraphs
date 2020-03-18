package jgraphs.core.node;

import java.util.ArrayList;

import com.google.inject.Inject;

import jgraphs.core.node.value.IMaxValueNode;
import jgraphs.core.state.IState;

public class Node extends AbstractNode {
    @Inject
    public Node(IState state, IMaxValueNode maxValueNode) {
    	super(state, maxValueNode);
    	this.predecessors = new ArrayList<INode>();
    	this.successors = new ArrayList<INode>();  	
    }
    
    @Override
    public INode createNewNode() {
    	var copy = new Node(this.state, this.maxValueNode);
   		copy.getPredecessors().addAll(this.getPredecessors());
   		copy.getSuccessors().addAll(this.getSuccessors());
        return copy;  
    }
}
