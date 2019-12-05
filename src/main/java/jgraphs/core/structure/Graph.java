package jgraphs.core.structure;

import jgraphs.core.node.INode;

public class Graph extends AbstractStructure implements IGraph {
    public Graph() {
    	super();
    }
          
	public void addNode(INode node) {
		super.addNewNode(node);
	}
   
}
