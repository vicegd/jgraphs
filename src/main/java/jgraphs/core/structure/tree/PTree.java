package jgraphs.core.structure.tree;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.logger.DefaultLogger;
import jgraphs.logger.ILogger;

public class PTree extends Tree {
	protected static final ILogger logger = new DefaultLogger(PTree.class);

	@Inject
    public PTree(INode root) {
    	super(root);
    	super.nodeNames = new ConcurrentHashMap<UUID, String>();
    	super.nodes = new ConcurrentHashMap<UUID, INode>();
    	super.nodeList = new CopyOnWriteArrayList<INode>(new ArrayList<INode>());
    	super.addNewNode(root);
    }
      
   
}
