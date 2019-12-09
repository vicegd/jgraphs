package jgraphs.core.structure;

import java.util.List;
import java.util.UUID;

import jgraphs.core.node.INode;
import jgraphs.core.situation.ISituation;

public interface IStructure {
	public UUID getId();
	
	public INode getFirst();
	
	public INode getLast();
	
	public INode getSecondToLast();
	
	public String getNodeName(UUID id);
	
	public INode getNode(UUID id);
	
	public List<INode> getNodeList();
	
	public void setFirstSituatiton(ISituation situation);
}
