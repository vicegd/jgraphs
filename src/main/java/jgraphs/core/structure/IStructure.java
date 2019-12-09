package jgraphs.core.structure;

import java.util.List;
import java.util.UUID;

import jgraphs.core.node.INode;

public interface IStructure {
	public UUID getId();
	
	public INode getFirst();
	
	public INode getLast();
	
	public INode getSecondToLast();
	
	public String getNodeName(UUID id);
	
	public INode getNode(UUID id);
	
	public List<INode> getNodeList();
}
