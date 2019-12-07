package jgraphs.core.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import jgraphs.core.node.INode;

public abstract class AbstractStructure implements IStructure {
	protected UUID id;
	protected HashMap<UUID, String> nodeNames;
	protected HashMap<UUID, INode> nodes;
	protected List<INode> nodeList;
	
    public AbstractStructure() {
    	this.id = UUID.randomUUID();
    	this.nodeNames = new HashMap<UUID, String>();
		this.nodes = new HashMap<UUID, INode>();
		this.nodeList = new ArrayList<INode>();
    }
	
    @Override
    public UUID getId() {
    	return this.id;
    }
	
	@Override
	public INode getFirst() {
		return this.nodeList.get(0);
	}
	
	@Override
	public INode getLast() {
		return this.nodeList.get(this.nodeList.size()-1);
	}
	
	@Override
	public String getNodeName(UUID id) {
		return this.nodeNames.get(id);
	}

	@Override
	public INode getNode(UUID id) {
		return this.nodes.get(id);
	}

	@Override
	public List<INode> getNodeList() {
		return this.nodeList;
	}
	
	protected void addNewNode(INode node) {
		if (!nodes.containsKey(node.getId())) {
			this.nodes.put(node.getId(), node);
			this.nodeNames.put(node.getId(), "Node" + (this.nodeNames.size() + 1));
			this.nodeList.add(node);
		}
	}
}
