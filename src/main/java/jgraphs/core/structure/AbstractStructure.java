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
}