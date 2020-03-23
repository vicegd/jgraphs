package jgraphs.subsystem.visualizer.graph;

import static guru.nidi.graphviz.model.Factory.mutGraph;

import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;

public class FinishedGraphVisualizer extends AbstractGraphVisualizer {
		
	@Override
	public void processFinishedEvent(IStructure structure, List<INode> result) {
		this.g = mutGraph("MCTS").setDirected(true);
		
		this.iterateTree(structure, structure.getFirst());
		
		var uniqueFolder = this.getUniqueFolderPath(structure);
		var folderPath = this.path + "/" + uniqueFolder + "/processFinished";
		var pictureFilePath = folderPath + "/processFinished.svg";
		var dotFilePath = folderPath + "/processFinished.dot";
		
		this.saveGraph(g, pictureFilePath, dotFilePath);
	}

}
