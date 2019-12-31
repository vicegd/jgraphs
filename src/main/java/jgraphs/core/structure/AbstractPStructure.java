package jgraphs.core.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import jgraphs.core.node.INode;

public abstract class AbstractPStructure extends AbstractStructure {
    public AbstractPStructure() {
    	this.id = UUID.randomUUID();
    	this.nodeNames = new ConcurrentHashMap<UUID, String>();
		this.nodes = new ConcurrentHashMap<UUID, INode>();
		this.nodeList = Collections.synchronizedList(new ArrayList<INode>());
    }

}
