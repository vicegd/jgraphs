package jgraphs.subsystem.visualizer;

import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;

public interface IVisualizer {
	public void processFinishedEvent(IStructure structure, List<INode> result);
	public void movementPerformedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber);
	public void iterationPerformedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber, int iterationNumber);
}
