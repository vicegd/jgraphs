package jgraphs.visualizer.graph;

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
	
	@Override
	public void movementPerformedEvent(ITree tree, INode sourceNode, INode winnerNode, int movementNumber) {
		g = mutGraph("MCTS").setDirected(true);
		
		iterateTree(tree, tree.getRoot(), sourceNode, winnerNode);
		
		var uniqueFolder = this.getUniqueFolderPath(tree);
		var folderPath = this.path + "/" + uniqueFolder + "/movement" +  movementNumber;
		var pictureFilePath = folderPath + "/snapshot.svg";
		var dotFilePath = folderPath + "/snapshot.dot";
		
		this.saveGraph(g, pictureFilePath, dotFilePath);
	}
	
	@Override
	public void processFinishedEvent(ITree tree, INode winnerNode) {
		this.g = mutGraph("MCTS").setDirected(true);
		
		this.iterateTree(tree, tree.getRoot());
		
		var uniqueFolder = this.getUniqueFolderPath(tree);
		var folderPath = this.path + "/" + uniqueFolder + "/processFinished";
		var pictureFilePath = folderPath + "/processFinished.svg";
		var dotFilePath = folderPath + "/processFinished.dot";
		
		this.saveGraph(g, pictureFilePath, dotFilePath);
	}

}
