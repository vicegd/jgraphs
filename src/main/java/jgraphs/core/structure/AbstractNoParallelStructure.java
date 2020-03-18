package jgraphs.core.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.naming.OperationNotSupportedException;

import jgraphs.core.node.INode;

public abstract class AbstractNoParallelStructure extends AbstractStructure {
    public AbstractNoParallelStructure() {
    	super();
    	this.nodeNames = new HashMap<UUID, String>();
		this.nodes = new HashMap<UUID, INode>();
		this.nodeList = new ArrayList<INode>();
    }
	
	@Override
	public void loadStructure(Map<UUID, String> nodeNames, Map<UUID, INode> nodes, List<INode> nodeList) {
		this.nodeNames = nodeNames;
		this.nodes = nodes;
		this.nodeList = nodeList;
	}
	
	@Override
	public void loadStructure(ConcurrentHashMap<UUID, String> nodeNames, ConcurrentHashMap<UUID, INode> nodes, CopyOnWriteArrayList<INode> nodeList) throws OperationNotSupportedException  {
		throw new OperationNotSupportedException();
	}
}

