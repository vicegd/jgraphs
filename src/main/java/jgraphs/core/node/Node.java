package jgraphs.core.node;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

import jgraphs.core.state.IState;

public class Node implements INode {
	protected UUID id;
	protected IState state;
	protected IMaxValueNode maxValueNode;
	protected List<INode> predecessors;
	protected List<INode> successors;

    @Inject
    public Node(IState state, IMaxValueNode maxValueNode) {
    	this.id = UUID.randomUUID();
    	this.predecessors = new ArrayList<INode>();
    	this.successors = new ArrayList<INode>();  	
		this.state = state.createNewState();
		this.state.setNode(this);
		this.maxValueNode = maxValueNode;
    }
    
    @Override
    public INode createNewNode() {
    	var copy = new Node(this.state, this.maxValueNode);
   		copy.getPredecessors().addAll(this.getPredecessors());
   		copy.getSuccessors().addAll(this.getSuccessors());
        return copy;  
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
	public boolean containsPredecessor(UUID id) {
		for (var node : this.predecessors) {
			if (node.getId().equals(id)) return true;
		}
		return false;
	}
	
	@Override
	public boolean containsSuccessor(UUID id) {
		for (var node : this.successors) {
			if (node.getId().equals(id)) return true;
		}
		return false;
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
    	return this.successors.get(selectRandom);
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
    
	@Override
	public boolean equals(Object obj) {
		var that = (Node)obj;
		if (!this.id.equals(that.id)) return false;
		if (!this.state.equals(that.state)) return false;
		for (var node : this.predecessors) {
			if (!that.containsPredecessor(node.getId())) return false;
		}
		for (var node : this.successors) {
			if (!that.containsSuccessor(node.getId())) return false;
		}
        return true;
	}
	

}
