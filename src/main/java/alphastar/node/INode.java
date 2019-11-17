package alphastar.node;

import java.util.List;
import java.util.UUID;

import alphastar.core.structure.IState;

public interface INode {
	public UUID getId();
	
    public IState getState();

    public void setState(IState state);

    public INode getParent();

    public void setParent(INode parent);

    public List<INode> getChildArray();

    public void setChildArray(List<INode> childArray);

    public INode getRandomChildNode();

    public INode getChildWithMaxScore();
    
    public void linkNodeAndState();
}