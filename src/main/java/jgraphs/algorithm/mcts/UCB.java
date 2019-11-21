package jgraphs.algorithm.mcts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.core.node.INode;

public class UCB {
	private static Logger log = LoggerFactory.getLogger(UCB.class);
	double c;
	
	public UCB() {
    	try (InputStream input = new FileInputStream("src/main/java/config.properties")) {
            var prop = new Properties();
            prop.load(input);
            this.c = Double.parseDouble(prop.getProperty("ucb.c"));
    	} catch (IOException ex) {
       		log.error(ex.getMessage());
    	}
	}
	
	public void setC(double c) {
		this.c = c;
	}
	
    public double getValue(int parentVisits, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        return (nodeWinScore / (double) nodeVisit) + this.c * Math.sqrt(Math.log(parentVisits) / (double) nodeVisit);
    }

    public INode findBestNode(INode node) {
        var totalVisits = node.getState().getVisitCount();
        return Collections.max(
          node.getChildArray(),
          Comparator.comparing(c -> getValue(totalVisits, c.getState().getScore(), c.getState().getVisitCount())));
    }
}
