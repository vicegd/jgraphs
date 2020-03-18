package jgraphs.core.structure.graph;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractParallelStructure;

public class PGraph extends AbstractParallelStructure implements IGraph {
	@Inject
    public PGraph(INode root) {
    	super();
    	super.addNewNode(root);
    }
          
	@Override
	public void addNode(INode node) {
		super.addNewNode(node);
	}
}
