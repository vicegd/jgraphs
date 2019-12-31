package jgraphs.core.structure.graph;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractPStructure;

public class PGraph extends AbstractPStructure implements IGraph {
    public PGraph() {
    	super();
    }
          
	public void addNode(INode node) {
		super.addNewNode(node);
	}
   
}
