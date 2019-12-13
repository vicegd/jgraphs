package jgraphs.visualizer.console;

import java.util.List;

import jgraphs.algorithm.mcts.budget.DefaultBudgetManager;
import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.utils.Logger;
import jgraphs.visualizer.IVisualizer;

public class SimpleConsoleVisualizer implements IVisualizer {
	private static org.slf4j.Logger log = Logger.getInstance().getLogger(SimpleConsoleVisualizer.class);
	@Override
	public void structureChangedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber, int iterationNumber, int status) {
		log.info("\n******************************STRUCTURE CHANGED***********************");
		log.info(String.format("Movement:%d Iteration:%d Tree:%s (%d nodes) from %s (%s) to %s (%s) Status:%d", 
				movementNumber, iterationNumber,
				structure.getId(), structure.getNodeList().size(),
				structure.getNodeName(sourceNode.getId()), sourceNode.getId(),
				structure.getNodeName(endNode.getId()), endNode.getId(), status));
		log.info("**********************************************************************");
	}
	
	@Override
	public void movementPerformedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber) {
		log.info("\n******************************MOVEMENT PERFORMED**********************");
		log.info(String.format("Movement:%d Tree:%s from %s (%s) to %s (%s)", movementNumber, 
				structure.getId(), structure.getNodeName(sourceNode.getId()), sourceNode.getId(),
				structure.getNodeName(endNode.getId()), endNode.getId()));
		log.info(endNode.toString());
		log.info("**********************************************************************");
	}

	@Override
	public void processFinishedEvent(IStructure structure, List<INode> result) {
		log.info("\n******************************PROCESS FINISHED************************");	
		log.info("The process has ended with the following results:");
		for (var i = 0; i < result.size(); i++) {
			var node = result.get(i);
			log.info(String.format("%d - Status:%d - %s (%s)", i+1, 
					node.getState().getSituation().checkStatus(), 
					structure.getNodeName(node.getId()), node.getId()));
		}
		log.info("**********************************************************************");
	}
	
}
