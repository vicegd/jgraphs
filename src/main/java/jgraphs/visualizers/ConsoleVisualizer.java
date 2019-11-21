package jgraphs.visualizers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public class ConsoleVisualizer implements IVisualizer {
	private static Logger log = LoggerFactory.getLogger(ConsoleVisualizer.class);
	
	@Override
	public void movementPerformedEvent(INode winnerNode) {
		log.debug(winnerNode.toString());
	}

	@Override
	public void treeChanged(ITree tree, INode sourceNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber) {
		// TODO Auto-generated method stub
		
	}
}
