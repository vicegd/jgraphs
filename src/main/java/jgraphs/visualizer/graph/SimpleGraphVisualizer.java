package jgraphs.visualizer.graph;

import static guru.nidi.graphviz.model.Factory.mutGraph;

import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public class SimpleGraphVisualizer extends AbstractGraphVisualizer {
		
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
