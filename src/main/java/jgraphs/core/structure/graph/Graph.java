package jgraphs.core.structure.graph;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractNPStructure;

public class Graph extends AbstractNPStructure implements IGraph {
    public Graph(INode root) {
    	super();
    	super.addNewNode(root);
    }
       
    @Override
	public void addNode(INode node) {
		super.addNewNode(node);
	}
   
}
