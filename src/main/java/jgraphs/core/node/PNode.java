package jgraphs.core.node;

import java.util.Collections;

import com.google.inject.Inject;

import jgraphs.core.state.IState;

public class PNode extends Node {	
    @Inject
    public PNode(IState state, IMaxValueNode maxValueNode) {
    	super(state, maxValueNode);
    	super.predecessors = Collections.synchronizedList(super.predecessors);
    	super.successors = Collections.synchronizedList(super.successors);  	
    }

}
