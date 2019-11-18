package jgraphs.visualizers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alphastar.core.structure.EGameStatus;
import jgraphs.node.INode;
import jgraphs.tree.ITree;

public class ConsoleVisualizer implements IVisualizer {
	private static Logger log = LoggerFactory.getLogger(ConsoleVisualizer.class);
	
	@Override
	public void nodeSimulated(INode node) {
		//log.debug(node.toString());
	}

	@Override
	public void newMovement(INode node) {
		log.debug(node.toString());
	}

	@Override
	public void treeChanged(ITree tree, INode sourceNode, INode nodeToExplore, EGameStatus result, int movementNumber, int iterationNumber) {
		// TODO Auto-generated method stub
		
	}
}
