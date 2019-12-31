package jgraphs.core.structure.tree;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractStructure;
import jgraphs.logger.ILogger;
import jgraphs.logger.DefaultLogger;
import jgraphs.utils.IllegalTreeOperationException;

public class Tree extends AbstractStructure implements ITree {
	protected static final ILogger logger = new DefaultLogger(Tree.class);

	@Inject
    public Tree(INode root) {
    	super();
    	try {
			this.addNode(root);
		} catch (IllegalTreeOperationException e) {
			logger.error(e.getMessage());
		}
    }
          
	public void addNode(INode node) throws IllegalTreeOperationException {
		if ((node.getPredecessors().size() == 0)&&(nodeList.size() != 0)) 
			throw new IllegalTreeOperationException("Error: Only the root node does not have a predecessor");
		if (node.getPredecessors().size() > 1) 
			throw new IllegalTreeOperationException("Error: Number of predecessors cannot be greater than 1");
		super.addNewNode(node);
	}
   
}
