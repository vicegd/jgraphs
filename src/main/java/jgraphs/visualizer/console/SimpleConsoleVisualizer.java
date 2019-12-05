package jgraphs.visualizer.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.visualizer.IVisualizer;

public class SimpleConsoleVisualizer implements IVisualizer {
	private static Logger log = LoggerFactory.getLogger(SimpleConsoleVisualizer.class);

	@Override
	public void structureChangedEvent(IStructure structure, INode sourceNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber) {
		log.info("The structure has changed. Movement:" + movementNumber + " Iteration:" + iterationNumber);
	}
	
	@Override
	public void movementPerformedEvent(IStructure structure, INode sourceNode, INode winnerNode, int movementNumber) {
		log.info(winnerNode.toString());
	}

	@Override
	public void processFinishedEvent(IStructure structure, INode winnerNode) {
		log.info("The process has ended. State:" + winnerNode.getState().getBoard().checkStatus());
		log.info("Last node is: " + structure.getNodeName(winnerNode.getId()));
	}
	
}
