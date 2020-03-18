package jgraphs.core.node.value;

import java.util.List;

import jgraphs.core.node.INode;

public interface IMaxValueNode {
	public INode getMaxScoreNode(List<INode> nodes, int participant);
}
