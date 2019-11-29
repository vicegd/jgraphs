package jgraphs.visualizer;

import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public interface IVisualizer {
	public void treeChangedEvent(ITree tree, INode sourceNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber);
	public void movementPerformedEvent(ITree tree, INode sourceNode, INode winnerNode, int movementNumber);
	public void processFinishedEvent(ITree tree, INode winnerNode);
}
