package jgraphs.core.node;

import java.util.concurrent.CopyOnWriteArrayList;

import com.google.inject.Inject;

import jgraphs.core.state.IState;

public class PNode extends Node {	
    @Inject
    public PNode(IState state, IMaxValueNode maxValueNode) {
    	super(state, maxValueNode);
    	super.predecessors = new CopyOnWriteArrayList<INode>(super.predecessors);
    	super.successors = new CopyOnWriteArrayList<INode>(super.successors);
    }

}
