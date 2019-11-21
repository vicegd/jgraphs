package jgraphs.algorithm.mcts.defaultpolicy;

import jgraphs.core.node.INode;

public interface IDefaultPolicy {
    public int simulation(INode node);
}