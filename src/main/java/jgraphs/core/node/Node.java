package jgraphs.core.node;

import java.util.ArrayList;

import com.google.inject.Inject;

import jgraphs.core.state.IState;

public class Node extends AbstractNode {
    @Inject
    public Node(IState state, IMaxValueNode maxValueNode) {
    	super(state, maxValueNode);
    	this.predecessors = new ArrayList<INode>();
    	this.successors = new ArrayList<INode>();  	
    }
}
