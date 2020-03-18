package jgraphs.core.structure;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import jgraphs.core.node.INode;
import jgraphs.core.situation.ISituation;

public abstract class AbstractStructure implements IStructure {
	protected UUID id;
	protected Map<UUID, String> nodeNames;
	protected Map<UUID, INode> nodes;
	protected List<INode> nodeList;
	
    public AbstractStructure() {
    	this.id = UUID.randomUUID();
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
	public void setFirstSituation(ISituation situation) {
		this.getFirst().getState().setSituation(situation);
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
