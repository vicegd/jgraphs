package jgraphs.algorithm.manager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.core.structure.graph.Graph;
import jgraphs.core.structure.tree.Tree;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.subsystem.statistics.IStatistic;
import jgraphs.subsystem.traceability.ITraceability;
import jgraphs.subsystem.visualizer.IVisualizer;
import jgraphs.utils.IllegalTreeOperationException;

public abstract class Manager {
	protected static final ILogger logger = new DefaultLogger(Manager.class);
	protected List<IVisualizer> visualizers;
	protected List<IStatistic> statistics;
	protected List<ITraceability> traceabilities;
	protected List<INode> results;
	protected int movementNumber;
	protected IStructure structure;
	
	public Manager() {
    	this.visualizers = new ArrayList<IVisualizer>();
    	this.statistics = new ArrayList<IStatistic>();
    	this.traceabilities = new ArrayList<ITraceability>();
    	this.results = new ArrayList<INode>();
    	this.movementNumber = 1;
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
	
	public int getMovementNumber() {
		return this.movementNumber;
	}
	
	public void incrementMovementNumber() {
		this.movementNumber++;
	}
	
    public void addVisualizer(IVisualizer visualizer) {
    	this.visualizers.add(visualizer);
    }
    
    public void addStatistic(IStatistic statistic) {
    	this.statistics.add(statistic);
    }
    
    public void addTraceability(ITraceability traceability) {
    	this.traceabilities.add(traceability);
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
    
    protected void pauseEvent(IStructure structure) {
    	for(ITraceability traceability : traceabilities) {
    		traceability.pause(structure);
    	}
    }
    
	protected void addNodesToTreeStructure(List<INode> nodes) {
		try {
			var tree = (Tree)this.structure;
			for (INode node : nodes) 
				tree.addNode(node);
		} catch (IllegalTreeOperationException e) {
			logger.error(e.getMessage());
		}
	}
	
	protected void addNodeToGraphStructure(INode node) {
		var graph = (Graph)this.structure;
		graph.addNode(node);
	}
	
}