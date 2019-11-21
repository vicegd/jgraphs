package jgraphs.core.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

import jgraphs.core.node.INode;

public class Tree implements ITree {
	private UUID id;
	private INode root;
	private HashMap<UUID, String> nodeNames;
	private HashMap<UUID, INode> nodes;
	private List<INode> nodeList;
    
	@Inject
    public Tree(INode root) {
    	this.id = UUID.randomUUID();
    	this.root = root;
    	this.nodeNames = new HashMap<UUID, String>();
		this.nodes = new HashMap<UUID, INode>();
		this.nodeList = new ArrayList<INode>();
    	this.addNode(this.root);
    }
       
    @Override
    public UUID getId() {
    	return this.id;
    }
    
    @Override
	public INode getRoot() {
        return this.root;
    }
    
	@Override
	public void addNode(INode node) {
		if (!nodes.containsKey(node.getId())) {
			this.nodes.put(node.getId(), node);
			this.nodeNames.put(node.getId(), "Node" + (this.nodeNames.size() + 1));
			this.nodeList.add(node);
		}
	}

	@Override
	public String getNodeName(UUID id) {
		return this.nodeNames.get(id);
	}

	@Override
	public INode getNode(UUID id) {
		return this.nodes.get(id);
	}

	@Override
	public List<INode> getNodeList() {
		return this.nodeList;
	}
}
