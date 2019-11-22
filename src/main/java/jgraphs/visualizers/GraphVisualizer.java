package jgraphs.visualizers;

import static guru.nidi.graphviz.model.Factory.mutGraph;

import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public class GraphVisualizer extends AbstractGraphVisualizer {
	@Override
	public void treeChangedEvent(ITree tree, INode sourceNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber) {
		g = mutGraph("MCTS").setDirected(true);
		
		iterateTree(tree, tree.getRoot(), sourceNode, nodeToExplore, result);
		
		var uniqueFolder = this.getUniqueFolderPath(tree);
		var folderPath = this.path + "/" + uniqueFolder + "/movement" +  movementNumber;
		var pictureFilePath = folderPath + "/iter" + iterationNumber + ".svg";
		var dotFilePath = folderPath + "/iter" + iterationNumber + ".dot";
		
		this.saveGraph(g, pictureFilePath, dotFilePath);
	}

}
