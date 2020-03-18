package jgraphs.core.process;

import java.util.ArrayList;
import java.util.List;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;

public abstract class AbstractSilentProcess {
	protected static final ILogger logger = new DefaultLogger(AbstractSilentProcess.class);
	protected List<INode> results;
	protected IStructure structure;
	
	public AbstractSilentProcess() {
    	this.results = new ArrayList<INode>();
	}

	public void execute(INode node) {
		this.run(node);
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
}