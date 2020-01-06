package jgraphs.algorithm.mcts.expansion;

import java.util.List;

import jgraphs.core.node.INode;

public interface IExpansionPolicy {
    public List<INode> expansion(INode promisingNode);
}
