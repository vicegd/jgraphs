package jgraphs.core.process;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.statistics.IStatistic;
import jgraphs.visualizer.IVisualizer;

public abstract class AbstractProcess {
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
	
    protected void structureChangedEvent(IStructure structure, INode currentNode, INode nodeToExplore, int result, int movementNumber, int iterationNumber) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.structureChangedEvent(structure, currentNode, nodeToExplore, result, movementNumber, iterationNumber);
    	}
    }
 
    protected void movementPerformedEvent(IStructure structure, INode currentNode, INode winnerNode, int movementNumber) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.movementPerformedEvent(structure, currentNode, winnerNode, movementNumber);
    	}
    }
    
    protected void processFinishedEvent(IStructure structure, INode winnerNode, Duration processDuration, Duration totalDuration) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.processFinishedEvent(structure, winnerNode);
    	}
    	for(IStatistic statistic : statistics) {
    		statistic.processFinishedEvent(structure, processDuration, totalDuration);
    	}
    }
}
