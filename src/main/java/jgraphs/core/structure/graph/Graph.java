package jgraphs.core.structure.graph;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractBasicStructure;

public class Graph extends AbstractBasicStructure implements IGraph {
    public Graph(INode root) {
    	super();
    	super.addNewNode(root);
    }
       
    @Override
	public void addNode(INode node) {
		super.addNewNode(node);
	}
   
}
