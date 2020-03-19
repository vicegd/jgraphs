package jgraphs.algorithm.mcts.selection;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import jgraphs.core.node.INode;
import jgraphs.utils.Config;

public class UCBSelectionPolicy implements ISelectionPolicy {
	private static final HashMap<String, String> config = Config.getConfig(UCBSelectionPolicy.class);
	private double c = 1.41;
	
	public UCBSelectionPolicy() {
        this.c = Double.parseDouble(config.get(Config.UCB_SELECTION_POLICY_C));
	}
	
	@Override
    public INode selection(INode rootNode) {
        var node = rootNode;

        while (node.getSuccessors().size() != 0) {
            node = this.findBestNode(rootNode.getState().getParticipantManager().getOpponent(), node);
        }
        return node;
    }
	
    protected INode findBestNode(int player, INode node) {
        var totalVisits = node.getState().getVisitCount();
        var result =  Collections.max(
          node.getSuccessors(),
          Comparator.comparing(c -> getValue(totalVisits, c.getState().getScore(player), c.getState().getVisitCount())));
        return result;
    }
	
    protected double getValue(int parentVisits, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        return (nodeWinScore / (double) nodeVisit) + this.c * Math.sqrt(Math.log(parentVisits) / (double) nodeVisit);
    }
}