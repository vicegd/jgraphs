package jgraphs.visualizers;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.swing.plaf.synth.ColorType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public class GraphVisualizer implements IVisualizer {
	private static Logger log = LoggerFactory.getLogger(GraphVisualizer.class);
	private String path;
	private MutableGraph g;
	
	public GraphVisualizer() {			
    	try (InputStream input = new FileInputStream("src/main/java/config.properties")) {
            var prop = new Properties();
            prop.load(input);
            this.path = prop.getProperty("graph.visualizer.path");
    	} catch (IOException ex) {
       		log.error(ex.getMessage());
    	}
	}
	
	@Override
	public void nodeSimulated(INode node) {
	}

	@Override
	public void newMovement(INode node) {
	}

	@Override
	public void treeChanged(ITree tree, INode sourceNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber) {
		g = mutGraph("MCTS").setDirected(true);
		var dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
		
		iterateTree(tree, tree.getRoot(), sourceNode, nodeToExplore, result);
		
		var uniqueFolder = dateFormat.format(new Date()) + "_" + tree.getId().toString().replace("-", "");
		var folderPath = this.path + "/" + uniqueFolder + "/movement" +  movementNumber;
		var pictureFilePath = folderPath + "/iter" + iterationNumber + ".svg";
		var dotFilePath = folderPath + "/iter" + iterationNumber + ".dot";
		
		try {
			Graphviz.fromGraph(g).render(Format.SVG).toFile(new File(pictureFilePath));
			Graphviz.fromGraph(g).render(Format.DOT).toFile(new File(dotFilePath));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	private void iterateTree(ITree tree, INode node, INode currentNode, INode nodeToExplore, int result) {
		var n1Id = node.getId().toString();
		var n1Info = getInfoFromNode(tree, node);
		var n1 = mutNode(n1Id).add(Label.html(n1Info)).add(Shape.CIRCLE).add(Style.FILLED);
		for (INode n : node.getChildArray()) {			
			var n2Id = n.getId().toString();
			var n2Info = getInfoFromNode(tree, n);
			var n2 = mutNode(n2Id).add(Label.html(n2Info)).add(Shape.CIRCLE).add(Style.FILLED);
			if (n.getId().equals(currentNode.getId())) {
				n2.add(Color.LIGHTSEAGREEN);
			}
			else if (n.getId().equals(nodeToExplore.getId())) {
				n2.add(Color.ROYALBLUE2);
			}
			
			var n1Ton2 = n1.addLink(n2);//.attrs().add(Color.LIGHTBLUE3);
	        g.add(n1Ton2);
	        
	        
			if (n.getId().equals(nodeToExplore.getId())) {
				var n3 = mutNode(UUID.randomUUID().toString()).add(Label.html("SIMULATED RESULT:" + result)).add(Shape.CIRCLE).add(Style.FILLED).add(Color.PLUM2);
				var link = n2.linkTo(n3).attrs().add(Color.RED).add(Style.DASHED);
				var n2Ton3 = n2.addLink(link);
		        g.add(n2Ton3);
			}
	        
	        
			iterateTree(tree, n, currentNode, nodeToExplore, result);
		}
	}
	
	private String getInfoFromNode(ITree tree, INode node) {
		return tree.getNodeName(node.getId()) + 
	        		"<br/>" + node.getState().getStateValuesToHTML() + 
	        		"<br/>" + node.getState().getBoard().getBoardValuesToHTML();
	    }

}
