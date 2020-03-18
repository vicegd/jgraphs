package jgraphs.algorithm.mcts.implementation;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;

import com.google.inject.Inject;

import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.expansion.IExpansionPolicy;
import jgraphs.algorithm.mcts.propagation.IPropagationPolicy;
import jgraphs.algorithm.mcts.selection.ISelectionPolicy;
import jgraphs.algorithm.mcts.simulation.ISimulationPolicy;
import jgraphs.core.node.INode;
import jgraphs.core.structure.tree.ITree;
import jgraphs.statistics.IStatistic;
import jgraphs.traceability.ITraceability;
import jgraphs.visualizer.IVisualizer;

public class PMCTS extends MCTS {  
	private ForkJoinPool pool;
	
	@Inject
    public PMCTS(ITree tree, 
    		ISelectionPolicy selectionPolicy, IExpansionPolicy expansionPolicy, ISimulationPolicy simulationPolicy, IPropagationPolicy propagationPolicy,  
    		IBudgetManager budgetManager) {
		super(tree, selectionPolicy, expansionPolicy, simulationPolicy, propagationPolicy, budgetManager);
		super.visualizers = new CopyOnWriteArrayList<IVisualizer>(super.visualizers);
		super.statistics = new CopyOnWriteArrayList<IStatistic>(super.statistics);
		super.traceabilities = new CopyOnWriteArrayList<ITraceability>(super.traceabilities);
		super.results = new CopyOnWriteArrayList<INode>(super.results); 
		this.pool = new ForkJoinPool();
    }
	
	@Override
	public void run(INode node) {
        while (!node.getState().getSituation().hasFinished()) {
            node = this.mcts(node); 
        }
	}
	
    protected INode mcts(INode node) {     
    	var task = new MCTSTask(this, node, 1, super.getBudgetManager().getIterations());

    	//System.out.println("--");
    	pool.invoke(task);
    	//System.out.println("---");

        var winnerNode = node.getSuccessorWithMaxValue(node.getState().getParticipantManager().getOpponent());
        
        super.movementPerformedEvent(super.getStructure(), node, winnerNode, super.getMovementNumber());
        super.pauseEvent(super.getStructure());
        super.incrementMovementNumber();

        if (winnerNode.getState().getSituation().checkStatus() != -1) {
        	super.addResult(winnerNode);	       	
        }
        
        return winnerNode;
    }
            
}