package jgraphs.subsystem.visualizer.graph;

import static guru.nidi.graphviz.model.Factory.mutGraph;

import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;

public class GraphVisualizer extends AbstractGraphVisualizer {
	
	@Override
	public void structureChangedEvent(IStructure structure, INode sourceNode, INode nodeToExplore, int movementNumber, int iterationNumber, int status) {
		g = mutGraph("MCTS").setDirected(true);
		
		iterateTree(structure, structure.getFirst(), sourceNode, nodeToExplore, status);
		
		var uniqueFolder = this.getUniqueFolderPath(structure);
		var folderPath = this.path + "/" + uniqueFolder + "/movement" +  movementNumber;
		var pictureFilePath = folderPath + "/iter" + iterationNumber + ".svg";
		var dotFilePath = folderPath + "/iter" + iterationNumber + ".dot";
		
		this.saveGraph(g, pictureFilePath, dotFilePath);
	}
	
	@Override
	public void movementPerformedEvent(IStructure structure, INode sourceNode, INode winnerNode, int movementNumber) {
		g = mutGraph("MCTS").setDirected(true);
		
		iterateTree(structure, structure.getFirst(), sourceNode, winnerNode);
		
		var uniqueFolder = this.getUniqueFolderPath(structure);
		var folderPath = this.path + "/" + uniqueFolder + "/movement" +  movementNumber;
		var pictureFilePath = folderPath + "/snapshot.svg";
		var dotFilePath = folderPath + "/snapshot.dot";
		
		this.saveGraph(g, pictureFilePath, dotFilePath);
	}
	
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
