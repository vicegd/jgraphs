package jgraphs.visualizers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public class SimpleConsoleVisualizer implements IVisualizer {
	private static Logger log = LoggerFactory.getLogger(SimpleConsoleVisualizer.class);

	@Override
	public void treeChangedEvent(ITree tree, INode sourceNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber) {
		log.debug("The tree has changed. Movement:" + movementNumber + " Iteration:" + iterationNumber);
	}
	
	@Override
	public void movementPerformedEvent(ITree tree, INode winnerNode) {
		log.debug(winnerNode.toString());
	}

	@Override
	public void processFinishedEvent(ITree tree, INode winnerNode) {
		log.debug("The process has ended. State:" + winnerNode.getState().getBoard().checkStatus());
		log.debug("Las node is: " + tree.getNodeName(winnerNode.getId()));
	}
}
