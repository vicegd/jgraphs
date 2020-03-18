package jgraphs.core.structure;

import java.util.UUID;

import jgraphs.core.situation.ISituation;

public abstract class AbstractStructure implements IStructure {
	protected UUID id;
	
    public AbstractStructure() {
    	this.id = UUID.randomUUID();
    }
	
    @Override
    public UUID getId() {
    	return this.id;
    }
	
	@Override
	public void setFirstSituation(ISituation situation) {
		this.getFirst().getState().setSituation(situation);
	}
<<<<<<< master
}
=======
	
	protected void addNewNode(INode node) {
		if (!nodes.containsKey(node.getId())) {
			this.nodes.put(node.getId(), node);
			this.nodeNames.put(node.getId(), "Node" + (this.nodeNames.size() + 1));
			this.nodeList.add(node);
		}
	}
}
>>>>>>> 16cccd1 Persistence API finished
