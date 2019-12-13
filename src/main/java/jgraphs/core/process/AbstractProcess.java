package jgraphs.core.process;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.core.structure.Graph;
import jgraphs.core.structure.IStructure;
import jgraphs.core.structure.Tree;
import jgraphs.statistics.IStatistic;
import jgraphs.utils.IllegalTreeOperationException;
import jgraphs.utils.Logger;
import jgraphs.visualizer.IVisualizer;

public abstract class AbstractProcess {
	private Duration totalDuration;
	private Duration processDuration;
	private List<IVisualizer> visualizers;
	private List<IStatistic> statistics;
	private List<INode> results;
	private Instant timer;
	private int movementNumber;
	private IStructure structure;
	protected static org.slf4j.Logger log = Logger.getInstance().getLogger(AbstractProcess.class);
	
	public AbstractProcess() {
		this.totalDuration = Duration.ZERO;
     	this.processDuration = Duration.ZERO;
    	this.visualizers = new ArrayList<IVisualizer>();
    	this.statistics = new ArrayList<IStatistic>();
    	this.results = new ArrayList<INode>();
    	this.movementNumber = 1;
	}
	
	public void execute(INode node) {
		this.initializeExecution();
		this.run(node);
    	this.finalizeExecution();
	}
	
	public abstract void run(INode node);
		
	public IStructure getStructure() {
		return this.structure;
	}
	
	public void setStructure(IStructure structure) {
		this.structure = structure;
	}
	
	public List<INode> getResults() {
		return this.results;
	}
	
	public void addResult(INode result) {
		this.results.add(result);
	}
	
	public INode getFirstResult() {
		return this.results.get(0);
	}
	
	public Instant getTimer() {
		return this.timer;
	}
	
	public int getMovementNumber() {
		return this.movementNumber;
	}
	
	public void incrementMovementNumber() {
		this.movementNumber++;
	}
	
    public void addVisualizer(IVisualizer visualizer) {
    	visualizers.add(visualizer);
    }
    
    public void addStatistic(IStatistic statistic) {
    	statistics.add(statistic);
    }
    	
    protected void structureChangedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber, int iterationNumber, int status) {
    	var time = Instant.now();
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.structureChangedEvent(structure, sourceNode, endNode, movementNumber, iterationNumber, status);
    	}
    	this.decrementProcessDuration(time);
    }
 
    protected void movementPerformedEvent(IStructure structure, INode sourceNode, INode endNode, int movementNumber) {
    	var time = Instant.now();
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.movementPerformedEvent(structure, sourceNode, endNode, movementNumber);
    	}
    	this.decrementProcessDuration(time);
    }
    
    protected void processFinishedEvent(IStructure structure, List<INode> result, Duration processDuration, Duration totalDuration) {
    	var time = Instant.now();
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.processFinishedEvent(structure, result);
    	}
    	this.decrementProcessDuration(time);
    	for(IStatistic statistic : statistics) {
    		statistic.processFinishedEvent(structure, processDuration, totalDuration);
    	}
    }
    
	protected void addNodeToTreeStructure(INode node) {
		try {
			var tree = (Tree)this.structure;
			tree.addNode(node);
		} catch (IllegalTreeOperationException e) {
			log.error(e.getMessage());
		}
	}
	
	protected void addNodeToGraphStructure(INode node) {
		var graph = (Graph)this.structure;
		graph.addNode(node);
	}
    
	private void initializeExecution() {
    	this.timer = Instant.now();
	}
	
	private void finalizeExecution() {
		this.incrementProcessDuration(this.timer); 
		this.incrementTotalDuration(this.timer);
    	this.processFinishedEvent(this.structure, this.results, this.processDuration, this.totalDuration);
	}
	
    private void incrementTotalDuration(Instant instant) {
    	this.totalDuration = this.totalDuration.plus(Duration.between(instant, Instant.now()));
    }
    
    private void incrementProcessDuration(Instant instant) {
    	this.processDuration = this.processDuration.plus(Duration.between(instant, Instant.now()));
    }
    
    private void decrementProcessDuration(Instant instant) {
    	this.processDuration = this.processDuration.minus(Duration.between(instant, Instant.now()));
    }
}