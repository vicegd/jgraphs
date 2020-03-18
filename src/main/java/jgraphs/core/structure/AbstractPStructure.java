package jgraphs.core.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.naming.OperationNotSupportedException;

import jgraphs.core.node.INode;

public abstract class AbstractPStructure extends AbstractStructure {
	protected ConcurrentHashMap<UUID, String> nodeNames;
	protected ConcurrentHashMap<UUID, INode> nodes;
	protected CopyOnWriteArrayList<INode> nodeList;
	
    public AbstractPStructure() {
    	super();
    	this.nodeNames = new ConcurrentHashMap<UUID, String>();
		this.nodes = new ConcurrentHashMap<UUID, INode>();
		this.nodeList = new CopyOnWriteArrayList<INode>(new ArrayList<INode>());
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
	public INode getSecondToLast() {
		return this.nodeList.get(this.nodeList.size()-2);
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
	
	@Override
	public void loadStructure(ConcurrentHashMap<UUID, String> nodeNames, ConcurrentHashMap<UUID, INode> nodes, CopyOnWriteArrayList<INode> nodeList) {
		this.nodeNames = nodeNames;
		this.nodes = nodes;
		this.nodeList = nodeList;
	}
	
	@Override
	public void loadStructure(Map<UUID, String> nodeNames, Map<UUID, INode> nodes, List<INode> nodeList) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}
	
	@Override
	public boolean equals(Object obj) {
		var that = (AbstractPStructure)obj;
		
		for (var n : this.nodeList) {
			if (!n.equals(that.getNode(n.getId()))) return false;
		}
		
        return true;
	}
	
	/**
	 * Should not be called from the outside
	 * @param node
	 */
	protected void addNewNode(INode node) {
		if (!nodes.containsKey(node.getId())) {
			this.nodes.put(node.getId(), node);
			this.nodeNames.put(node.getId(), "Node" + (this.nodeNames.size() + 1));
			this.nodeList.add(node);
		}
	}
}
