package jgraphs.statistics;

import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;

public class ConsoleStatistic implements IStatistic {
	private static Logger log = LoggerFactory.getLogger(ConsoleStatistic.class);
	StatisticData data = new StatisticData();

	@Override
	public void processFinishedEvent(ITree tree, Duration processDuration, Duration totalDuration) {
		this.getData(tree);
		log.info("\n\n******************************STATISTICS******************************");	
		log.info(String.format("Elapsed process time: %d,%d seconds", processDuration.getSeconds(), processDuration.getNano()));
		log.info(String.format("Elapsed total time: %d,%d seconds", totalDuration.getSeconds(), totalDuration.getNano()));
		log.info(String.format("Depth of the tree: %d", data.treeDepth));
		log.info(String.format("Width of the tree: %d", data.treeWidth));
		log.info(String.format("Number of generated nodes: %d", data.generatedNodes));	
		log.info(String.format("Number of nodes that have been explored: %d", data.exploredNodes));
		log.info(String.format("Number of nodes that have not been explored: %d", data.notExploredNodes));
		log.info(String.format("Number of visits: %d", data.visits));
		log.info(String.format("Relative visits: %f%%", data.relativeVisits));
		log.info(String.format("Top visited nodes"));
		for (UUID id : data.topVisitedNodes) {
			var node = tree.getNode(id);
			log.info(String.format("\tNode: %s \t Visits: %d", tree.getNodeName(id), node.getState().getVisitCount()));
		}
		log.info(String.format("Top ranked nodes"));
		for (UUID id : data.topRankedNodes) {
			var node = tree.getNode(id);
			log.info(String.format("\tNode: %s \t Visits: %d", tree.getNodeName(id), node.getState().serializeScores()));
		}
		log.info("**********************************************************************");
	}
	
	private void getData(ITree tree) {
		this.data.generatedNodes = tree.getNodeList().size();
		this.data.exploredNodes = tree.getNodeList().stream()
				.filter(n -> n.getState().getVisitCount() > 0)
				.count();
		this.data.notExploredNodes = data.generatedNodes - data.exploredNodes;
		this.data.visits = tree.getNodeList().stream()
				.map(n -> n.getState().getVisitCount())
				.mapToInt(Integer::valueOf)
				.sum();
		this.data.relativeVisits = (float)data.generatedNodes / (float)data.visits;
		iterateTree(tree.getRoot(), 1);
	}
	
	private void iterateTree(INode node, int depth) {
		var width = 0;
		if (depth > this.data.treeDepth)
			this.data.treeDepth = depth;
		for (INode n : node.getChildArray()) {	
			if (n.getState().getVisitCount() > 0) {
				iterateTree(n, depth + 1);		
				width++;
			}
		}
		if (width > this.data.treeWidth)
			this.data.treeWidth = width;
	}
}
