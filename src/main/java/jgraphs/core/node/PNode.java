package jgraphs.core.node;

import java.util.concurrent.CopyOnWriteArrayList;

import com.google.inject.Inject;

import jgraphs.core.node.value.IMaxValueNode;
import jgraphs.core.state.IState;

public class PNode extends AbstractNode  {	    
    @Inject
    public PNode(IState state, IMaxValueNode maxValueNode) {
    	super(state, maxValueNode);
    	super.predecessors = new CopyOnWriteArrayList<INode>(super.predecessors);
    	super.successors = new CopyOnWriteArrayList<INode>(super.successors); 	
    }
    
    @Override
    public INode createNewNode() {
    	var copy = new PNode(this.state, this.maxValueNode);
   		copy.getPredecessors().addAll(this.getPredecessors());
   		copy.getSuccessors().addAll(this.getSuccessors());
        return copy;  
    }
}
