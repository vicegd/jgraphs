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
	}
	
	public void execute(INode node) {
    	var totalTimer = Instant.now();
    	this.processTimer = Instant.now();
    	
    	executeAlgorithm(node);
    	
    	this.totalDuration = totalDuration.plus(Duration.between(totalTimer, Instant.now()));
    	this.processFinishedEvent(this.structure, this.processDuration, this.totalDuration);
	}
	
	protected abstract void executeAlgorithm(INode node);
	
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
    }
    
    protected void processFinishedEvent(IStructure structure, Duration processDuration, Duration totalDuration) {
    	for(IStatistic statistic : statistics) {
    		statistic.processFinishedEvent(structure, processDuration, totalDuration);
    	}
    }
    
}
