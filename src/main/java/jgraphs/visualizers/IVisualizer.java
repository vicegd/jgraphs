package jgraphs.visualizers;

import alphastar.core.structure.EGameStatus;
import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public interface IVisualizer {
	public void nodeSimulated(INode node);
	public void newMovement(INode node);
	public void treeChanged(ITree tree, INode sourceNode, INode nodeToExplore, EGameStatus result, int movementNumber, int iterationNumber);
}
