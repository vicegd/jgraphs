package jgraphs.core.process;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;
import jgraphs.statistics.IStatistic;
import jgraphs.visualizer.IVisualizer;

public abstract class AbstractProcess implements IProcess {
	protected Duration totalDuration;
	protected Duration processDuration;
	protected List<IVisualizer> visualizers;
	protected List<IStatistic> statistics;
	
	public AbstractProcess() {
		this.totalDuration = Duration.ZERO;
     	this.processDuration = Duration.ZERO;
    	this.visualizers = new ArrayList<IVisualizer>();
    	this.statistics = new ArrayList<IStatistic>();
	}
	
    public void addVisualizer(IVisualizer visualizer) {
    	visualizers.add(visualizer);
    }
    
    public void addStatistic(IStatistic statistic) {
    	statistics.add(statistic);
    }
	
    protected void treeChangedEvent(ITree tree, INode currentNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.treeChangedEvent(tree, currentNode, nodeToExplore, result, movementNumber, iterationNumber);
    	}
    }
 
    protected void movementPerformedEvent(ITree tree, INode currentNode, INode winnerNode, int movementNumber) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.movementPerformedEvent(tree, currentNode, winnerNode, movementNumber);
    	}
    }
    
    protected void processFinishedEvent(ITree tree, INode winnerNode, Duration processDuration, Duration totalDuration) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.processFinishedEvent(tree, winnerNode);
    	}
    	for(IStatistic statistic : statistics) {
    		statistic.processFinishedEvent(tree, processDuration, totalDuration);
    	}
    }
}
