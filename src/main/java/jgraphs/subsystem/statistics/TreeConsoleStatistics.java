package jgraphs.subsystem.statistics;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;

public class TreeConsoleStatistics implements IStatistic {
	protected static final ILogger logger = new DefaultLogger(TreeConsoleStatistics.class);
	protected List<TreeStatisticsInfo> info = new ArrayList<TreeStatisticsInfo>();
	protected HashMap<Integer, Integer> generatedWidths;
	protected HashMap<Integer, Integer> exploredWidths;

	@Override
	public void checkpointEvent(IStructure structure, Instant start, Instant end) {
		this.generatedWidths = new HashMap<Integer, Integer>();
		this.exploredWidths = new HashMap<Integer, Integer>();
		var data = this.getData(structure);
		logger.info("\n******************************STATISTICS******************************");	
		logger.info(String.format("Elapsed total time: %d nanoseconds (%d seconds)", Duration.between(start, end).getNano(), Duration.between(start, end).getSeconds()));
		logger.info(String.format("Depth of the tree: %d", data.treeDepth));
		logger.info(String.format("Width of the tree: %d", data.treeWidth));
		logger.info(String.format("Width of the explored tree: %d", data.exploredTreeWidth));
		logger.info(String.format("Number of nodes: %d", data.nodes));	
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
			logger.info(String.format("\t**Participant " + (i+1)));
			for (var n : data.topRankedNodes.get(i)) {
				logger.info(String.format("\tNode: %s (%s) \t Visits: %d \t Scores: %s \t Total scores: %f", structure.getNodeName(n.getId()), n.getId(), n.getState().getVisitCount(), n.getState().serializeScores(), n.getState().getTotalScores()));
			}
		}
		logger.info("**********************************************************************");
	}
	
	public List<TreeStatisticsInfo> getStatisticsInfo() {
		return this.info;
	}
	
	public TreeStatisticsInfo getLastStatisticsInfo() {
		return this.info.get(this.info.size()-1);
	}
	
	private TreeStatisticsInfo getData(IStructure structure) {
		var data = new TreeStatisticsInfo();	
		iterateTree(data, structure.getFirst(), 1);
		
		data.treeWidth = Collections.max(generatedWidths.entrySet(), Map.Entry.comparingByValue()).getValue();
		data.exploredTreeWidth = Collections.max(exploredWidths.entrySet(), Map.Entry.comparingByValue()).getValue();
		
		data.nodes = structure.getNodeList().size();
		data.exploredNodes = structure.getNodeList().stream()
				.filter(n -> n.getState().getVisitCount() > 0)
				.count();
		data.notExploredNodes = data.nodes - data.exploredNodes;
		data.visits = structure.getNodeList().stream()
				.map(n -> n.getState().getVisitCount())
				.mapToInt(Integer::valueOf)
				.sum();
		data.visitsPerGeneratedNodes = (float)data.visits / (float)data.nodes;
		data.visitsPerExploredNodes =  (float)data.visits / (float)data.exploredNodes;
		data.topVisitedNodes = structure.getNodeList().stream()
				.sorted((n1, n2) -> n2.getState().getVisitCount() - n1.getState().getVisitCount())
				.limit(10)
				.collect(Collectors.toList());

		data.topRankedNodes = new ArrayList<>();
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
			data.topRankedNodes.add(rankedList);
		}
		this.info.add(data);
		return data;
	}
	
	private void iterateTree(TreeStatisticsInfo data, INode node, int depth) {
		if (depth > data.treeDepth)
			data.treeDepth = depth;
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
			iterateTree(data, n, depth + 1);		
		}
	}
}
