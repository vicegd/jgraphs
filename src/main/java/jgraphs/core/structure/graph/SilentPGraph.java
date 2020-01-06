package jgraphs.core.structure.graph;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import jgraphs.core.node.INode;

public class SilentPGraph extends SilentGraph {
    public SilentPGraph() {
    	super();
    	super.nodeNames = new ConcurrentHashMap<UUID, String>();
    	super.nodes = new ConcurrentHashMap<UUID, INode>();
    	super.nodeList = new CopyOnWriteArrayList<INode>(new ArrayList<INode>());
    }   
}
