package jgraphs.subsystem.visualizer.console;

import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.subsystem.visualizer.IVisualizer;

public class SimpleConsoleVisualizer implements IVisualizer {
	protected static final ILogger logger = new DefaultLogger(SimpleConsoleVisualizer.class);
	@Override
	public void structureChangedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber, int iterationNumber, int status) {
		logger.info("\n******************************STRUCTURE CHANGED***********************");
		logger.info(String.format("Movement:%d Iteration:%d Tree:%s (%d nodes) from %s (%s) to %s (%s) Status:%d", 
				movementNumber, iterationNumber,
				structure.getId(), structure.getNodeList().size(),
				structure.getNodeName(sourceNode.getId()), sourceNode.getId(),
				structure.getNodeName(endNode.getId()), endNode.getId(), status));
		logger.info("**********************************************************************");
	}
	
	@Override
	public void movementPerformedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber) {
		logger.info("\n******************************MOVEMENT PERFORMED**********************");
		logger.info(String.format("Movement:%d Tree:%s from %s (%s) to %s (%s)", movementNumber, 
				structure.getId(), structure.getNodeName(sourceNode.getId()), sourceNode.getId(),
				structure.getNodeName(endNode.getId()), endNode.getId()));
		logger.info(endNode.toString());
		logger.info("**********************************************************************");
	}

	@Override
	public void processFinishedEvent(IStructure structure, List<INode> result) {
		logger.info("\n******************************PROCESS FINISHED************************");	
		logger.info("The process has ended with the following results:");
		for (var i = 0; i < result.size(); i++) {
			var node = result.get(i);
			logger.info(String.format("%d - Status:%d - %s (%s)", i+1, 
					node.getState().getSituation().checkStatus(), 
					structure.getNodeName(node.getId()), node.getId()));
		}
		logger.info("**********************************************************************");
	}
	
}
