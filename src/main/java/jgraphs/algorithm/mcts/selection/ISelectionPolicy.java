package jgraphs.algorithm.mcts.selection;

import jgraphs.core.node.INode;

public interface ISelectionPolicy {
    public INode selection(INode rootNode);
}
