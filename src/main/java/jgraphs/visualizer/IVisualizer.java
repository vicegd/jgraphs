package jgraphs.visualizer;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;

public interface IVisualizer {
	public void structureChangedEvent(IStructure structure, INode sourceNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber);
	public void movementPerformedEvent(IStructure structure, INode sourceNode, INode winnerNode, int movementNumber);
	public void processFinishedEvent(IStructure structure, INode winnerNode);
}
