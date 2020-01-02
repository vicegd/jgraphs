package jgraphs.core.structure.tree;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractStructure;
import jgraphs.logger.ILogger;
import jgraphs.logger.DefaultLogger;
import jgraphs.utils.IllegalTreeOperationException;

public class SilentTree extends AbstractStructure implements ITree {
	protected static final ILogger logger = new DefaultLogger(SilentTree.class);

	@Inject
    public SilentTree(INode root) {
    	super();
    	this.addNewNode(root);
    }
          
	public void addNode(INode node) throws IllegalTreeOperationException {
	}
   
}
