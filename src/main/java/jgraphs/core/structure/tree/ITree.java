package jgraphs.core.structure.tree;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.utils.IllegalTreeOperationException;

public interface ITree extends IStructure {	
	public void addNode(INode node) throws IllegalTreeOperationException;
}