package jgraphs.algorithm.mcts.expansion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.utils.Dependency;

public class GenerateAllExpansionPolicy implements IExpansionPolicy {
    @Override
	public List<INode> expansion(INode promisingNode) {
    	List<INode> nodes = Collections.synchronizedList(new ArrayList<INode>());
    	var possibleStates = promisingNode.getState().nextStates();
	    possibleStates.forEach(state -> {
	    	var newNode = Dependency.getInstance().createNodeInstance();
	    	newNode.setState(state);
	    	newNode.getPredecessors().add(promisingNode);
	        promisingNode.getSuccessors().add(newNode);   
	        nodes.add(newNode);
	    });
	    return nodes;
    }
}
