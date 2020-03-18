package jgraphs.core.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.naming.OperationNotSupportedException;

import jgraphs.core.node.INode;

public abstract class AbstractParallelStructure extends AbstractStructure {
    public AbstractParallelStructure() {
    	super();
    	this.nodeNames = new ConcurrentHashMap<UUID, String>();
		this.nodes = new ConcurrentHashMap<UUID, INode>();
		this.nodeList = new CopyOnWriteArrayList<INode>(new ArrayList<INode>());
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
}
