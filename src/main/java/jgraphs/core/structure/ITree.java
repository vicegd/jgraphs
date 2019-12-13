package jgraphs.core.structure;

import jgraphs.core.node.INode;
import jgraphs.utils.IllegalTreeOperationException;

public interface ITree extends IStructure {	
	public void addNode(INode node) throws IllegalTreeOperationException;
}