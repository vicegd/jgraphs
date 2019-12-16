package jgraphs.algorithm.mcts.treepolicy;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import jgraphs.core.node.INode;
import jgraphs.utils.Config;

public class UCB implements ITreePolicy {
	private static final HashMap<String, String> config = Config.getConfig(UCB.class);
	private double winScore = 1;
	private double drawScore = 0.5;
	private double loseScore = 0;
	private double c = 1.41;
	
	public UCB() {
        this.winScore = Double.parseDouble(config.get(Config.UCB_WIN_SCORE));
        this.drawScore = Double.parseDouble(config.get(Config.UCB_DRAW_SCORE));
        this.loseScore = Double.parseDouble(config.get(Config.UCB_LOSE_SCORE));
        this.c = Double.parseDouble(config.get(Config.UCB_C));
	}
	
	@Override
    public INode findBestNode(int player, INode node) {
        var totalVisits = node.getState().getVisitCount();
        return Collections.max(
          node.getSuccessors(),
          Comparator.comparing(c -> getValue(totalVisits, c.getState().getScore(player), c.getState().getVisitCount())));
    }
	
    @Override
    public double getWinScore() {
    	return this.winScore;
    }
    
    @Override
    public double getDrawScore() {
    	return this.drawScore;
    }
    
    @Override
    public double getLoseScore() {
    	return this.loseScore;
    }
	
    private double getValue(int parentVisits, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        return (nodeWinScore / (double) nodeVisit) + this.c * Math.sqrt(Math.log(parentVisits) / (double) nodeVisit);
    }
}