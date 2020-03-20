package jgraphs.algorithm.mcts.implementation;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;

import com.google.inject.Inject;

import jgraphs.algorithm.manager.AbstractManager;
import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.expansion.IExpansionPolicy;
import jgraphs.algorithm.mcts.propagation.IPropagationPolicy;
import jgraphs.algorithm.mcts.selection.ISelectionPolicy;
import jgraphs.algorithm.mcts.simulation.ISimulationPolicy;
import jgraphs.core.node.INode;
import jgraphs.core.structure.tree.ITree;
import jgraphs.subsystem.statistics.IStatistic;
import jgraphs.subsystem.traceability.ITraceability;
import jgraphs.subsystem.visualizer.IVisualizer;

public class PMCTS extends AbstractManager {
	private ForkJoinPool pool;
	protected ISelectionPolicy selectionPolicy;
	protected IExpansionPolicy expansionPolicy;
	protected ISimulationPolicy simulationPolicy;
	protected IPropagationPolicy propagationPolicy;
	protected IBudgetManager budgetManager;
    
	@Inject
    public PMCTS(ITree tree, 
    		ISelectionPolicy selectionPolicy, IExpansionPolicy expansionPolicy, ISimulationPolicy simulationPolicy, IPropagationPolicy propagationPolicy, 
    		IBudgetManager budgetManager) {
		super();
		super.setStructure(tree);		
		super.visualizers = new CopyOnWriteArrayList<IVisualizer>(super.visualizers);
		super.statistics = new CopyOnWriteArrayList<IStatistic>(super.statistics);
		super.traceabilities = new CopyOnWriteArrayList<ITraceability>(super.traceabilities);
		super.results = new CopyOnWriteArrayList<INode>(super.results); 
		this.selectionPolicy = selectionPolicy;
		this.expansionPolicy = expansionPolicy;
		this.simulationPolicy = simulationPolicy;
		this.propagationPolicy = propagationPolicy;
		this.budgetManager = budgetManager;
		this.pool = new ForkJoinPool();
    }
	
	@Override
	public void run(INode node) {
        while (!node.getState().getSituation().hasFinished()) {
            node = this.mcts(node); 
        }
	}
                    
	protected INode mcts(INode node) {     
		var task = new MCTSTask(this, node, 1, this.getBudgetManager().getIterations());
	    pool.invoke(task);

	    var winnerNode = node.getSuccessorWithMaxValue(node.getState().getParticipantManager().getOpponent());
	    super.incrementMovementNumber();

	    if (winnerNode.getState().getSituation().checkStatus() != -1) {
	    	super.addResult(winnerNode);	       	
	    }
	        
	    return winnerNode;
	}

    protected INode selection(INode rootNode) {
        return selectionPolicy.selection(rootNode);
    }

    protected void expansion(INode promisingNode) {
	    super.addNodesToTreeStructure(expansionPolicy.expansion(promisingNode));
    }

    protected int simulation(INode node) {
    	return simulationPolicy.simulation(node);
    }
    
    protected void propagation(INode nodeToExplore, int result) {
    	propagationPolicy.propagation(nodeToExplore, result);
    }
    
    public IBudgetManager getBudgetManager() {
    	return this.budgetManager;
    }
    
    public void setTrainers(boolean[] trainers) {
    	this.propagationPolicy.setTrainers(trainers);
    }
}