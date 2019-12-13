package jgraphs.algorithm.mcts.treepolicy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

import org.slf4j.Logger;

import jgraphs.core.node.INode;
import jgraphs.logging.Logging;

public class UCB implements ITreePolicy {
	private static Logger log = Logging.getInstance().getLogger(UCB.class);
	private double winScore = 1;
	private double drawScore = 0.5;
	private double loseScore = 0;
	private double c = 1.41;
	
	public UCB() {
    	try (InputStream input = new FileInputStream("src/main/java/config.properties")) {
            var prop = new Properties();
            prop.load(input);
            this.winScore = Double.parseDouble(prop.getProperty("ucb.winScore"));
            this.drawScore = Double.parseDouble(prop.getProperty("ucb.drawScore"));
            this.loseScore = Double.parseDouble(prop.getProperty("ucb.loseScore"));
            this.c = Double.parseDouble(prop.getProperty("ucb.c"));
    	} catch (IOException ex) {
       		log.error(ex.getMessage());
    	}
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
