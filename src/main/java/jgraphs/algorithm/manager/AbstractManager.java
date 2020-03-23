package jgraphs.algorithm.manager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.jfree.util.Log;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.core.structure.graph.Graph;
import jgraphs.core.structure.tree.ITree;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.subsystem.statistics.IStatistic;
import jgraphs.subsystem.traceability.ITraceability;
import jgraphs.subsystem.visualizer.IVisualizer;
import jgraphs.utils.IllegalTreeOperationException;

public abstract class AbstractManager {
	protected static final ILogger logger = new DefaultLogger(AbstractManager.class);
	protected List<IVisualizer> visualizers;
	protected List<IStatistic> statistics;
	protected List<ITraceability> traceabilities;
	protected List<INode> results;
	protected int movementNumber;
	protected IStructure structure;
	protected Instant initTime;
	
	public AbstractManager() {
		this.initTime = Instant.now();
    	this.visualizers = new ArrayList<IVisualizer>();
    	this.statistics = new ArrayList<IStatistic>();
    	this.traceabilities = new ArrayList<ITraceability>();
    	this.results = new ArrayList<INode>();
    	this.movementNumber = 0;
	}
	
	public abstract void run(INode node);
	
	public void run() {
        this.run(this.structure.getFirst());
	}
		
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
	
	public int getMovementNumber() {
		return this.movementNumber;
	}
	
	public Instant getInitInstant() {
		return this.initTime;
	}
	
	public void incrementMovementNumber() {
		this.movementNumber++;
	}
	
    public void addVisualizer(IVisualizer visualizer) {
    	this.visualizers.add(visualizer);
    }
    
    public void addStatistics(IStatistic statistic) {
    	this.statistics.add(statistic);
    }
    
    public void addTraceability(ITraceability traceability) {
    	this.traceabilities.add(traceability);
    }
    
    protected void checkpointEvent() {
    	for(IStatistic statistic : statistics) {
    		statistic.checkpointEvent(this.structure, this.initTime, Instant.now());
    	}
    }
    
    protected void pauseEvent() {
    	for(ITraceability traceability : traceabilities) {
    		traceability.pause(this.structure);
    	}
    }
    
    protected void processFinishedEvent() {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.processFinishedEvent(this.structure, this.results);
    	}
    }
    
    protected void movementPerformedEvent(INode sourceNode, INode endNode) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.movementPerformedEvent(this.structure, sourceNode, endNode, this.movementNumber);
    	}
    }
    
    protected void iterationPerformedEvent(INode sourceNode, INode endNode, int iterationNumber) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.iterationPerformedEvent(this.structure, sourceNode, endNode, this.movementNumber, iterationNumber);
    	}
    }
    
	protected void addNodeToTreeStructure(INode node) {
		var nodes = new ArrayList<INode>();
		nodes.add(node);
		this.addNodesToTreeStructure(nodes);
	}
    
	protected void addNodesToTreeStructure(List<INode> nodes) {
		var tree = (ITree)this.structure;
		for (INode node : nodes)
			try {
				tree.addNode(node);
			} catch (IllegalTreeOperationException e) {
				Log.error(e.getMessage());
			}
	}
	
	protected void addNodeToGraphStructure(INode node) {
		var graph = (Graph)this.structure;
		graph.addNode(node);
	}
}