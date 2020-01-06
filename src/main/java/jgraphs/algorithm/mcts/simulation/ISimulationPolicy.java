package jgraphs.algorithm.mcts.simulation;

import jgraphs.core.node.INode;

public interface ISimulationPolicy {
    public int simulation(INode node);
}