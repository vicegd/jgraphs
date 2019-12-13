package jgraphs.visualizer.graph;

import static guru.nidi.graphviz.model.Factory.mutNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import jgraphs.algorithm.mcts.budget.DefaultBudgetManager;
import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.utils.Logger;
import jgraphs.visualizer.IVisualizer;

public abstract class AbstractGraphVisualizer implements IVisualizer {
	private static org.slf4j.Logger log = Logger.getInstance().getLogger(AbstractGraphVisualizer.class);
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
	public void structureChangedEvent(IStructure structure, INode sourceNode, INode end, int movementNumber, int iterationNumber, int status) {
	}

	@Override
	public void movementPerformedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber) {
	}
	
	@Override
	public void processFinishedEvent(IStructure structure, List<INode> result) {
	}
	
	protected void iterateTree(IStructure structure, INode node, INode currentNode, INode nodeToExplore, int result) {
		var n1Id = node.getId().toString();
		var n1Info = getInfoFromNode(structure, node);
		var n1 = mutNode(n1Id).add(Label.html(n1Info)).add(Shape.CIRCLE).add(Style.FILLED);
		if ((currentNode != null)&&(node.getId().equals(currentNode.getId()))) {
			n1.add(Color.LIGHTSEAGREEN);
		}
		for (INode n : node.getSuccessors()) {	
			if (n.getState().getVisitCount() > 0) {
				var n2Id = n.getId().toString();
				var n2Info = getInfoFromNode(structure, n);
				var n2 = mutNode(n2Id).add(Label.html(n2Info)).add(Shape.CIRCLE).add(Style.FILLED);
				
				if (currentNode != null) {
					if (n.getId().equals(currentNode.getId())) {
						n2.add(Color.LIGHTSEAGREEN);
					}
				}
				if (nodeToExplore != null) {
					if (n.getId().equals(nodeToExplore.getId())) {
						n2.add(Color.ROYALBLUE2);
					}
				}		
				
				var n1Ton2 = n1.addLink(n2);
		        g.add(n1Ton2);
				
		        if (result != Integer.MIN_VALUE) {	        	
					if (n.getId().equals(nodeToExplore.getId())) {
						var n3 = mutNode(UUID.randomUUID().toString()).add(Label.html("SIMULATED RESULT:" + result)).add(Shape.CIRCLE).add(Style.FILLED).add(Color.PLUM2);
						var link = n2.linkTo(n3).attrs().add(Color.RED).add(Style.DASHED);
						var n2Ton3 = n2.addLink(link);
				        g.add(n2Ton3);
					}
		        }
				
				iterateTree(structure, n, currentNode, nodeToExplore, result);				
			}
		}
	}
	
	protected void iterateTree(IStructure structure, INode node, INode currentNode, INode nodeToExplore) {
		iterateTree(structure, node, currentNode, nodeToExplore, Integer.MIN_VALUE);
	}
	
	protected void iterateTree(IStructure structure, INode node) {
		iterateTree(structure, node, null, null, Integer.MIN_VALUE);
	}
	
	protected String getInfoFromNode(IStructure structure, INode node) {
		return ((structure.getNodeName(node.getId()) != null)?structure.getNodeName(node.getId()):node.getId().toString()) + 
	        		"<br/>" + node.getState().getStateValuesToHTML() + 
	        		"<br/>" + node.getState().getSituation().getValuesToHTML();
	}
	
	protected String getUniqueFolderPath(IStructure structure) {
		return dateFormat.format(new Date()) + "_" + structure.getId().toString().replace("-", "");
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
