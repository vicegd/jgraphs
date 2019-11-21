package jgraphs.core.node;

import java.util.List;
import java.util.UUID;

import jgraphs.core.state.IState;

public interface INode {
    public INode createNewNode();
    
	public UUID getId();
	
    public IState getState();

    public void setState(IState state);

    public INode getParent();

    public void setParent(INode parent);

    public List<INode> getChildArray();

    public void setChildArray(List<INode> childArray);

    public INode getRandomChildNode();

    public INode getChildWithMaxValue(int player);
}