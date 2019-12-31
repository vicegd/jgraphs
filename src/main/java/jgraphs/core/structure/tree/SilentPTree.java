package jgraphs.core.structure.tree;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.structure.AbstractPStructure;
import jgraphs.logger.DefaultLogger;
import jgraphs.logger.ILogger;
import jgraphs.utils.IllegalTreeOperationException;

public class SilentPTree extends AbstractPStructure implements ITree {
	protected static final ILogger logger = new DefaultLogger(SilentPTree.class);

	@Inject
    public SilentPTree(INode root) {
    	super();
    	try {
			this.addNode(root);
		} catch (IllegalTreeOperationException e) {
			logger.error(e.getMessage());
		}
    }
          
	public void addNode(INode node) throws IllegalTreeOperationException {
	}
   
}
