package jgraphs.subsystem.statistics;

import java.util.List;

import jgraphs.core.node.INode;

public class TreeStatisticsInfo {
	public long treeDepth;
	public long treeWidth;
	public long exploredTreeWidth;
	public long nodes;
	public long exploredNodes;
	public long notExploredNodes;
	public long visits;
	public double visitsPerGeneratedNodes;
	public double visitsPerExploredNodes;
	public List<INode> topVisitedNodes;
	public List<List<INode>> topRankedNodes;
}