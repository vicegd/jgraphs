package alphastar.tree;

import java.util.List;
import java.util.UUID;

import alphastar.node.INode;

public interface ITree {
	public UUID getId();
	
	public void initializeTree();
	
	public INode getRoot();
	
	public void addNode(INode node);
	
	public String getNodeName(UUID id);
	
	public INode getNode(UUID id);
	
	public List<INode> getNodeList();
}