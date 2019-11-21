package jgraphs.visualizers;

import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public interface IVisualizer {
	public void nodeSimulated(INode node);
	public void newMovement(INode node);
	public void treeChanged(ITree tree, INode sourceNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber);
}
