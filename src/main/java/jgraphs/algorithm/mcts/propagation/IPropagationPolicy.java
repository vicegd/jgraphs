package jgraphs.algorithm.mcts.propagation;

import jgraphs.core.node.INode;

public interface IPropagationPolicy {
    public void propagation(INode nodeToExplore, int result);
    public double getWinScore();
    public double getDrawScore();
    public double getLoseScore();
    public void setTrainers(boolean[] trainers);
}
