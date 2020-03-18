package jgraphs.statistics;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;

public class TreeConsoleStatistic implements IStatistic {
	protected static final ILogger logger = new DefaultLogger(TreeConsoleStatistic.class);
	HashMap<Integer, Integer> generatedWidths = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> exploredWidths = new HashMap<Integer, Integer>();
	TreeStatisticData data = new TreeStatisticData();

	@Override
	public void processFinishedEvent(IStructure structure, Duration processDuration, Duration totalDuration) {
		this.getData(structure);
		logger.info("\n******************************STATISTICS******************************");	
		logger.info(String.format("Elapsed process time: %d,%d seconds", processDuration.getSeconds(), processDuration.getNano()));
		logger.info(String.format("Elapsed total time: %d,%d seconds", totalDuration.getSeconds(), totalDuration.getNano()));
		logger.info(String.format("Depth of the tree: %d", data.treeDepth));
		logger.info(String.format("Width of the tree: %d", data.generatedTreeWidth));
		logger.info(String.format("Width of the explored tree: %d", data.exploredTreeWidth));
		logger.info(String.format("Number of nodes: %d", data.generatedNodes));	
		logger.info(String.format("Number of explored nodes: %d", data.exploredNodes));
		logger.info(String.format("Number of nodes that have not been explored: %d", data.notExploredNodes));
		logger.info(String.format("Number of visits: %d", data.visits));
		logger.info(String.format("Visits per node: %f", data.visitsPerGeneratedNodes));
		logger.info(String.format("Visits per explored node: %f", data.visitsPerExploredNodes));
		logger.info(String.format("Top visited nodes"));
		for (var n : data.topVisitedNodes) {
			logger.info(String.format("\tNode: %s (%s) \t Visits: %d \t Scores: %s \t Total scores: %f", structure.getNodeName(n.getId()), n.getId(), n.getState().getVisitCount(), n.getState().serializeScores(), n.getState().getTotalScores()));
		}
		logger.info(String.format("Top ranked nodes"));
		
		for (var i = 0; i < structure.getFirst().getState().getParticipantManager().getNumberOfParticipants(); i++) {
			logger.info(String.format("\t**Position " + (i+1)));
			for (var n : data.topRankedNodes.get(i)) {
				logger.info(String.format("\tNode: %s (%s) \t Visits: %d \t Scores: %s \t Total scores: %f", structure.getNodeName(n.getId()), n.getId(), n.getState().getVisitCount(), n.getState().serializeScores(), n.getState().getTotalScores()));
			}
		}
		logger.info("**********************************************************************");
	}
	
	private void getData(IStructure structure) {
		iterateTree(structure.getFirst(), 1);
		this.data.generatedTreeWidth = Collections.max(generatedWidths.entrySet(), Map.Entry.comparingByValue()).getValue();
		this.data.exploredTreeWidth = Collections.max(exploredWidths.entrySet(), Map.Entry.comparingByValue()).getValue();
		this.data.generatedNodes = structure.getNodeList().size();
		this.data.exploredNodes = structure.getNodeList().stream()
				.filter(n -> n.getState().getVisitCount() > 0)
				.count();
		this.data.notExploredNodes = data.generatedNodes - data.exploredNodes;
		this.data.visits = structure.getNodeList().stream()
				.map(n -> n.getState().getVisitCount())
				.mapToInt(Integer::valueOf)
				.sum();
		this.data.visitsPerGeneratedNodes = (float)data.visits / (float)data.generatedNodes;
		this.data.visitsPerExploredNodes =  (float)data.visits / (float)data.exploredNodes;
		this.data.topVisitedNodes = structure.getNodeList().stream()
				.sorted((n1, n2) -> n2.getState().getVisitCount() - n1.getState().getVisitCount())
				.limit(10)
				.collect(Collectors.toList());

		this.data.topRankedNodes = new ArrayList<>();
		var q = new PriorityQueue<Integer>();		
		for (var i = 0; i < structure.getFirst().getState().getParticipantManager().getNumberOfParticipants(); i++) {
			q.add(i);
		}
		for (var i = 0; i < structure.getFirst().getState().getParticipantManager().getNumberOfParticipants(); i++) {
			var rankedList = structure.getNodeList().stream()
					.sorted((n1, n2) -> Double.compare(n2.getState().getScore(q.peek()+1), n1.getState().getScore(q.peek()+1)))
					.limit(10)
					.collect(Collectors.toList());
			q.poll();
			this.data.topRankedNodes.add(rankedList);
		}
	}
	
	private void iterateTree(INode node, int depth) {
		if (depth > this.data.treeDepth)
			this.data.treeDepth = depth;
		if (generatedWidths.get(depth) != null) 
			generatedWidths.put(depth, generatedWidths.get(depth) + 1);
		else
			generatedWidths.put(depth, 1);
		if (node.getState().getVisitCount() > 0)
		if (exploredWidths.get(depth) != null) 
			exploredWidths.put(depth, exploredWidths.get(depth) + 1);
		else
			exploredWidths.put(depth, 1);
		
		for (INode n : node.getSuccessors()) {	
			iterateTree(n, depth + 1);		
		}
	}
}
