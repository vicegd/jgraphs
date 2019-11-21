package jgraphs.algorithm.mcts.treepolicy;

import jgraphs.core.node.INode;

public interface ITreePolicy {
	public void setC(double c);
	
    public double getValue(int parentVisits, double nodeWinScore, int nodeVisit);

    public INode findBestNode(int player, INode node);
}
