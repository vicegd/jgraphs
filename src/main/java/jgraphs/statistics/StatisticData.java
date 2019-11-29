package jgraphs.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StatisticData {
	public long treeDepth;
	public long treeWidth;
	public long generatedNodes;
	public long exploredNodes;
	public long notExploredNodes;
	public long visits;
	public double relativeVisits;
	public List<UUID> topVisitedNodes = new ArrayList<UUID>();
	public List<UUID> topRankedNodes = new ArrayList<UUID>();
}