package jgraphs.subsystem.visualizer.graph;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

import java.util.List;

import guru.nidi.graphviz.attribute.Label;
import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;

public class ShapeGraphVisualizer extends AbstractGraphVisualizer {
	
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
	
	protected void iterateTree(IStructure structure, INode node) {
		var n1Id = node.getId().toString();
		var n1 = mutNode(n1Id).add(Label.html(" "));

		for (INode n : node.getSuccessors()) {	
			if (n.getState().getVisitCount() > 0) {
				var n2Id = n.getId().toString();
				var n2 = mutNode(n2Id).add(Label.html(" "));
				
				var n1Ton2 = n1.addLink(n2);
		        g.add(n1Ton2);
								
				iterateTree(structure, n);				
			}
		}
	}

}
