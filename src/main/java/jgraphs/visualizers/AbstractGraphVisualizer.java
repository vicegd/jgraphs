package jgraphs.visualizers;

import static guru.nidi.graphviz.model.Factory.mutNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public class AbstractGraphVisualizer implements IVisualizer {
	protected static Logger log = LoggerFactory.getLogger(AbstractGraphVisualizer.class);
	protected String path;
	protected MutableGraph g;
	protected SimpleDateFormat dateFormat;
	
	public AbstractGraphVisualizer() {		
		this.dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
    	try (InputStream input = new FileInputStream("src/main/java/config.properties")) {
            var prop = new Properties();
            prop.load(input);
            this.path = prop.getProperty("graph.visualizer_path");
    	} catch (IOException ex) {
       		log.error(ex.getMessage());
    	}
	}
	
	@Override
	public void treeChangedEvent(ITree tree, INode sourceNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber) {
	}

	@Override
	public void movementPerformedEvent(ITree tree, INode winnerNode) {
	}
	
	@Override
	public void processFinishedEvent(ITree tree, INode winnerNode) {
	}
	
	protected void iterateTree(ITree tree, INode node) {
		var n1Id = node.getId().toString();
		var n1Info = getInfoFromNode(tree, node);
		var n1 = mutNode(n1Id).add(Label.html(n1Info)).add(Shape.CIRCLE).add(Style.FILLED);
		for (INode n : node.getChildArray()) {	
			if (n.getState().getVisitCount() > 0) {
				var n2Id = n.getId().toString();
				var n2Info = getInfoFromNode(tree, n);
				var n2 = mutNode(n2Id).add(Label.html(n2Info)).add(Shape.CIRCLE).add(Style.FILLED);
				
				var n1Ton2 = n1.addLink(n2);
		        g.add(n1Ton2);		       
				
				iterateTree(tree, n);				
			}
		}
	}
	
	protected void iterateTree(ITree tree, INode node, INode currentNode, INode nodeToExplore, int result) {
		var n1Id = node.getId().toString();
		var n1Info = getInfoFromNode(tree, node);
		var n1 = mutNode(n1Id).add(Label.html(n1Info)).add(Shape.CIRCLE).add(Style.FILLED);
		for (INode n : node.getChildArray()) {	
			if (n.getState().getVisitCount() > 0) {
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
	}
	
	protected String getInfoFromNode(ITree tree, INode node) {
		return ((tree.getNodeName(node.getId()) != null)?tree.getNodeName(node.getId()):node.getId().toString()) + 
	        		"<br/>" + node.getState().getStateValuesToHTML() + 
	        		"<br/>" + node.getState().getBoard().getBoardValuesToHTML();
	}
	
	protected String getUniqueFolderPath(ITree tree) {
		return dateFormat.format(new Date()) + "_" + tree.getId().toString().replace("-", "");
	}

	protected void saveGraph(MutableGraph g, String pictureFilePath, String dotFilePath) {
		try {
			Graphviz.fromGraph(g).render(Format.SVG).toFile(new File(pictureFilePath));
			Graphviz.fromGraph(g).render(Format.DOT).toFile(new File(dotFilePath));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
