package jgraphs.core.structure.tree;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractPStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.utils.IllegalTreeOperationException;

public class PTree extends AbstractPStructure implements ITree {
	protected static final ILogger logger = new DefaultLogger(PTree.class);

	@Inject
    public PTree(INode root) {
    	super();
    	super.addNewNode(root);
    }
          
	@Override
	public void addNode(INode node) throws IllegalTreeOperationException {
		if ((node.getPredecessors().size() == 0)&&(nodeList.size() != 0)) 
			throw new IllegalTreeOperationException("Error: Only the root node does not have a predecessor");
		if (node.getPredecessors().size() > 1) 
			throw new IllegalTreeOperationException("Error: Number of predecessors cannot be greater than 1");
		super.addNewNode(node);
	}
      
}
