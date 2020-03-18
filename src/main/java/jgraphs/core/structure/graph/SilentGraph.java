package jgraphs.core.structure.graph;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractNPStructure;

public class SilentGraph extends AbstractNPStructure implements IGraph {
    public SilentGraph(INode root) {
    	super();
    	super.addNewNode(root);
    }
          
    @Override
	public void addNode(INode node) {
	}
   
}
