package jgraphs.core.node;

import java.util.List;
import java.util.UUID;

import jgraphs.core.node.value.IMaxValueNode;
import jgraphs.core.state.IState;

public abstract class AbstractNode implements INode {
	protected UUID id;
	protected IState state;
	protected IMaxValueNode maxValueNode;
	protected List<INode> predecessors;
	protected List<INode> successors;
            
    public AbstractNode(IState state, IMaxValueNode maxValueNode) {
    	this.id = UUID.randomUUID();
		this.state = state.createNewState();
		this.state.setNode(this);
		this.maxValueNode = maxValueNode;
    }
    
    @Override
    public UUID getId() {
    	return this.id;
    }
    
	@Override
	public void setId(UUID id) {
		this.id = id;	
	}

    @Override
    public IState getState() {
        return this.state;
    }

    @Override
    public void setState(IState state) {    	
        this.state = state;
        this.state.setNode(this);
    }

    @Override
    public List<INode> getPredecessors() {
        return this.predecessors;
    }

    @Override
    public void setPredecessors(List<INode> predecessors) {
        this.predecessors = predecessors;
    }

    @Override
    public List<INode> getSuccessors() {
        return this.successors;
    }

    @Override
    public void setSuccessors(List<INode> successors) {
        this.successors = successors;
    }

    @Override
    public INode getRandomSuccessorNode() {
    	if (this.successors.size() == 0) return null;
   		var selectRandom = (int) (Math.random() * this.successors.size());
    	var node = this.successors.get(selectRandom);
    	//node.getState().setBeingExplored(true); //QUITARLO DE AQUI!!!!!!!!!!!!!!!!!
    	return node;
    }

    @Override
    public INode getSuccessorWithMaxValue(int participant) {
        return this.maxValueNode.getMaxScoreNode(this.successors, participant);
    }
       
    @Override
    public String toString() {
    	var sb = new StringBuilder();
    	sb.append("Node: \n");
    	sb.append("\tId: " + this.id + "\n");
    	for (INode n : this.predecessors) {
    		sb.append("\tPredecessor: " + n.getId() + "\n"); 
    	}
    	for (INode n : this.successors) {
    		sb.append("\tSuccessor: " + n.getId() + "\n"); 
    	}
    	sb.append(this.state.toString());
        return sb.toString();
    }

}
