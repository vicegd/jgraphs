package jgraphs.core.structure.graph;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractNoParallelStructure;

public class Graph extends AbstractNoParallelStructure implements IGraph {
	@Inject
    public Graph(INode root) {
    	super();
    	super.addNewNode(root);
    }
       
    @Override
	public void addNode(INode node) {
		super.addNewNode(node);
	}
   
}
