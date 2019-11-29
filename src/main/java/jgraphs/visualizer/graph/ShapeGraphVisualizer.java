package jgraphs.visualizer.graph;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

import guru.nidi.graphviz.attribute.Label;
import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public class ShapeGraphVisualizer extends AbstractGraphVisualizer {
	
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
	
	protected void iterateTree(ITree tree, INode node) {
		var n1Id = node.getId().toString();
		var n1 = mutNode(n1Id).add(Label.html(" "));

		for (INode n : node.getChildArray()) {	
			if (n.getState().getVisitCount() > 0) {
				var n2Id = n.getId().toString();
				var n2 = mutNode(n2Id).add(Label.html(" "));
				
				var n1Ton2 = n1.addLink(n2);
		        g.add(n1Ton2);
								
				iterateTree(tree, n);				
			}
		}
	}

}
