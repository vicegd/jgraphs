package jgraphs.core.structure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.utils.IllegalTreeOperationException;

public class Tree extends AbstractStructure implements ITree {
	private static Logger log = LoggerFactory.getLogger(Tree.class);

	@Inject
    public Tree(INode root) {
    	super();
    	try {
			this.addNode(root);
		} catch (IllegalTreeOperationException e) {
			log.error(e.getMessage());
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
