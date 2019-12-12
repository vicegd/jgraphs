package jgraphs.core.node;

import java.util.List;
import java.util.UUID;

import jgraphs.core.state.IState;

public interface INode {
    public INode createNewNode();
    
	public UUID getId();
	
	public void setId(UUID id);
	
	public boolean containsPredecessor(UUID id);

	public boolean containsSuccessor(UUID id);
	
    public IState getState();

    public void setState(IState state);

    public List<INode> getPredecessors();

    public void setPredecessors(List<INode> predecessors);

    public List<INode> getSuccessors();

    public void setSuccessors(List<INode> successors);

    public INode getRandomSuccessorNode();

    public INode getSuccessorWithMaxValue(int participant);
}