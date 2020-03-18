package jgraphs.core.structure.graph;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractPStructure;

public class PGraph extends AbstractPStructure implements IGraph {
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
