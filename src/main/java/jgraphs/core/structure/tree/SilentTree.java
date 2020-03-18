package jgraphs.core.structure.tree;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractBasicStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.utils.IllegalTreeOperationException;

public class SilentTree extends AbstractBasicStructure implements ITree {
	protected static final ILogger logger = new DefaultLogger(SilentTree.class);

	@Inject
    public SilentTree(INode root) {
    	super();
    	super.addNewNode(root);
    }
          
	@Override
	public void addNode(INode node) throws IllegalTreeOperationException {
	}
   
}
