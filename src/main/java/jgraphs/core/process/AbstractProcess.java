package jgraphs.core.process;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.statistics.IStatistic;
import jgraphs.visualizer.IVisualizer;

public abstract class AbstractProcess {
	protected static Logger log = LoggerFactory.getLogger(AbstractProcess.class);
	protected Duration totalDuration;
	protected Duration processDuration;
	protected Instant totalTimer;
	protected Instant processTimer;
	protected List<IVisualizer> visualizers;
	protected List<IStatistic> statistics;
	protected List<INode> result;
	protected IStructure structure;
	protected int movementNumber;
	
	public AbstractProcess() {
		this.totalDuration = Duration.ZERO;
     	this.processDuration = Duration.ZERO;
    	this.visualizers = new ArrayList<IVisualizer>();
    	this.statistics = new ArrayList<IStatistic>();
    	this.result = new ArrayList<INode>();
    	this.movementNumber = 1;
	}
		
	public IStructure getStructure() {
		return this.structure;
	}
	
	public List<INode> getResult() {
		return this.result;
	}
	
    public void addVisualizer(IVisualizer visualizer) {
    	visualizers.add(visualizer);
    }
    
    public void addStatistic(IStatistic statistic) {
    	statistics.add(statistic);
    }
	
    protected void structureChangedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber, int iterationNumber, int status) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.structureChangedEvent(structure, sourceNode, endNode, movementNumber, iterationNumber, status);
    	}
    }
 
    protected void movementPerformedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.movementPerformedEvent(structure, sourceNode, endNode, movementNumber);
    	}
    }
    
    protected void processFinishedEvent(IStructure structure, List<INode> result, Duration processDuration, Duration totalDuration) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.processFinishedEvent(structure, result);
    	}
    	for(IStatistic statistic : statistics) {
    		statistic.processFinishedEvent(structure, processDuration, totalDuration);
    	}
    }
    
    protected void incrementTotalDuration(Instant instant) {
    	this.totalDuration = this.totalDuration.plus(Duration.between(instant, Instant.now()));
    }
    
    protected void incrementProcessDuration(Instant instant) {
    	this.processDuration = this.processDuration.plus(Duration.between(instant, Instant.now()));
    }
    
    protected void decrementProcessDuration(Instant instant) {
    	this.processDuration = this.processDuration.minus(Duration.between(instant, Instant.now()));
    }
    
}
