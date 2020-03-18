package jgraphs.core.node.value;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jgraphs.core.node.INode;

public class ScoreMaxValueNode implements IMaxValueNode {
	@Override
	public INode getMaxScoreNode(List<INode> nodes, int participant) {
        return Collections.max(nodes, Comparator.comparing(node -> {
            return node.getState().getScore(participant); 
        }));
	}
}
