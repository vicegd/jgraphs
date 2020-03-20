package jgraphs.algorithm.mcts.implementation;

import java.util.concurrent.RecursiveAction;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;

public class MCTSTask extends RecursiveAction {
	protected static final ILogger logger = new DefaultLogger(MCTSTask.class);
	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD = 1250;
	private PMCTS mcts;
	private INode node;
	private long start;
	private long end;
    
	@Inject
    public MCTSTask(PMCTS mcts, INode node, long start, long end) {
		this.mcts = mcts;
		this.node = node;
		this.start = start;
		this.end = end;
    }	          

	@Override
	protected void compute() {
		if ((end - start) < THRESHOLD) {
	    	for (var i = start; i < end; i++) {   	    	
	            // Phase 1 - Selection
		        var promisingNode = mcts.selection(node);
		        // Phase 2 - Expansion
		        if ((promisingNode.getState().getVisitCount() >= 1)||(promisingNode.equals(mcts.getStructure().getFirst())))
		            mcts.expansion(promisingNode); //Only expand it if it is the root node or it has already been visited yet					
	            // Phase 3 - Simulation
	            var nodeToExplore = promisingNode;
	            if (promisingNode.getSuccessors().size() > 0) {
	                nodeToExplore = promisingNode.getRandomSuccessorNode();
	            }
	            var result = mcts.simulation(nodeToExplore); //****
	            // Phase 4 - Update
	            mcts.propagation(nodeToExplore, result);
	        }
		}
		else {
			long middle = (start + end)/2;
			invokeAll(new MCTSTask(mcts, node, start, middle), new MCTSTask(mcts, node, middle, end));
		}
	}

}