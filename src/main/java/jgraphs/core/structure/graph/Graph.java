package jgraphs.core.structure.graph;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractStructure;

public class Graph extends AbstractStructure implements IGraph {
    public Graph() {
    	super();
    }
          
	public void addNode(INode node) {
		super.addNewNode(node);
	}
   
}
