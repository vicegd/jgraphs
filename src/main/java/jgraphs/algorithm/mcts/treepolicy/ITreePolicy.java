package jgraphs.algorithm.mcts.treepolicy;

import jgraphs.core.node.INode;

public interface ITreePolicy {
    public INode findBestNode(int player, INode node);
        
    public double getWinScore();
    
    public double getDrawScore();
    
    public double getLoseScore();
}
