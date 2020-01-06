package jgraphs.algorithm.mcts.implementation;

import java.util.concurrent.RecursiveAction;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.logger.DefaultLogger;
import jgraphs.logger.ILogger;
import jgraphs.persistence.FilePersistence;

public class MCTSTask extends RecursiveAction {
	protected static final ILogger logger = new DefaultLogger(MCTSTask.class);
	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD = 1250;
	private PMCTS mcts;
	private INode node;
	private int start;
	private int end;
    
	@Inject
    public MCTSTask(PMCTS mcts, INode node, int start, int end) {
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
		        logger.debug(Thread.currentThread().getName() + " 2");
		        // Phase 2 - Expansion
		        if ((promisingNode.getState().getVisitCount() >= 1)||(promisingNode.equals(mcts.getStructure().getFirst())))
		            mcts.expansion(promisingNode); //Only expand it if it is the root node or it has already been visited yet					
		        logger.debug(Thread.currentThread().getName() + " 3");
	            // Phase 3 - Simulation
	            var nodeToExplore = promisingNode;
	            if (promisingNode.getSuccessors().size() > 0) {
	                nodeToExplore = promisingNode.getRandomSuccessorNode();
	            }
	            logger.debug(Thread.currentThread().getName() + " 4");
	            var result = mcts.simulation(nodeToExplore);
	            logger.debug(Thread.currentThread().getName() + " 5");
	            // Phase 4 - Update
	            mcts.propagation(nodeToExplore, result);
	            logger.debug(Thread.currentThread().getName() + " 6");
	            //mcts.structureChangedEvent(mcts.getStructure(), node, nodeToExplore, mcts.getMovementNumber(), i, result);
	            //mcts.pauseEvent(mcts.getStructure());
	            logger.debug(Thread.currentThread().getName() + " I:" + i + " START:" + start + " END:" + end);
	        }
		}
		else {
			int middle = (start + end)/2;
			invokeAll(new MCTSTask(mcts, node, start, middle), new MCTSTask(mcts, node, middle, end));
		}
	}

}