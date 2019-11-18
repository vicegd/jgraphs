package jgraphs.node;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import jpgrahs.state.IState;


public class Node implements INode {
	private static Logger log = LoggerFactory.getLogger(Node.class);
	private UUID id;
	private IState state;
	private INode parent;
	private List<INode> childArray;

    @Inject
    public Node(IState state) {
    	this.id = UUID.randomUUID();
    	this.childArray = new ArrayList<>();  	
		try {
			this.state = state.getClass().getDeclaredConstructor(INode.class, IState.class).newInstance(this, state);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			log.error(e.getMessage());
		}
    }
    
    @Override
    public INode copy() {
    	var newNode = new Node(this.state);
        newNode.parent = (this.getParent() != null)?this.getParent():null;
        Collections.copy(newNode.childArray, this.getChildArray());
        return newNode;  
    }
           
    @Override
    public UUID getId() {
    	return this.id;
    }

    @Override
    public IState getState() {
        return this.state;
    }

    @Override
    public void setState(IState state) {
        this.state = state;
    }

    @Override
    public INode getParent() {
        return this.parent;
    }

    @Override
    public void setParent(INode parent) {
        this.parent = parent;
    }

    @Override
    public List<INode> getChildArray() {
        return this.childArray;
    }

    @Override
    public void setChildArray(List<INode> childArray) {
        this.childArray = childArray;
    }

    @Override
    public INode getRandomChildNode() {
    	if (this.childArray.size() == 0) return null;
   		var selectRandom = (int) (Math.random() * this.childArray.size());
    	return this.childArray.get(selectRandom);
    }

    @Override
    public INode getChildWithMaxScore() {
        return Collections.max(this.childArray, Comparator.comparing(c -> {
            return c.getState().getWinScore(); 
        }));
    }
       
    @Override
    public String toString() {
    	var sb = new StringBuilder();
    	sb.append("Node: \n");
    	sb.append("\tId: " + this.id + "\n");
    	sb.append("\tParent: " + ((this.parent != null)?this.parent.getId():"Null") + "\n"); 
    	for (INode n : this.childArray) {
    		sb.append("\tChild: " + n.getId() + "\n"); 
    	}
    	sb.append(this.state.toString());
        return sb.toString();
    }

}
