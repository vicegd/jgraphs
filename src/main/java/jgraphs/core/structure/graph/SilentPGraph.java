package jgraphs.core.structure.graph;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractParallelStructure;

public class SilentPGraph extends AbstractParallelStructure implements IGraph {
	@Inject
    public SilentPGraph(INode root) {
    	super();
    	super.addNewNode(root);
    }
          
	@Override
	public void addNode(INode node) {
	}
}
