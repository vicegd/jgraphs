package jgraphs.subsystem.visualizer;

import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;

public interface IVisualizer {
	public void structureChangedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber, int iterationNumber, int status);
	public void movementPerformedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber);
	public void processFinishedEvent(IStructure structure, List<INode> result);
}
