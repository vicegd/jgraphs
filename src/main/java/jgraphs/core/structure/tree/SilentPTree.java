package jgraphs.core.structure.tree;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractPStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.utils.IllegalTreeOperationException;

public class SilentPTree extends AbstractPStructure implements ITree {
	protected static final ILogger logger = new DefaultLogger(SilentPTree.class);

	@Inject
    public SilentPTree(INode root) {
    	super();
    	super.addNewNode(root);
    }
          
	@Override
	public void addNode(INode node) throws IllegalTreeOperationException {
	}
             
}
