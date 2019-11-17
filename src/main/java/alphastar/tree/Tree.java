package alphastar.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

import alphastar.node.INode;

public class Tree implements ITree {
	@Inject private INode root;
	private UUID id;
	private HashMap<UUID, String> nodeNames;
	private HashMap<UUID, INode> nodes;
	private List<INode> nodeList;
    
    public Tree() {
    	this.id = UUID.randomUUID();
    	this.nodeNames = new HashMap<UUID, String>();
		this.nodes = new HashMap<UUID, INode>();
		this.nodeList = new ArrayList<INode>();
    }
       
    @Override
    public UUID getId() {
    	return this.id;
    }
    
    @Override
    public void initializeTree() {
    	this.addNode(this.root);
    	this.root.linkNodeAndState();
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
