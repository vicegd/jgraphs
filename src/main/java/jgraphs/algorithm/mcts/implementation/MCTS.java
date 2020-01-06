package jgraphs.algorithm.mcts.implementation;

import com.google.inject.Inject;

import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.expansion.IExpansionPolicy;
import jgraphs.algorithm.mcts.propagation.IPropagationPolicy;
import jgraphs.algorithm.mcts.selection.ISelectionPolicy;
import jgraphs.algorithm.mcts.simulation.ISimulationPolicy;
import jgraphs.core.node.INode;
import jgraphs.core.process.AbstractProcess;
import jgraphs.core.structure.tree.ITree;

public class MCTS extends AbstractProcess {
	protected ISelectionPolicy selectionPolicy;
	protected IExpansionPolicy expansionPolicy;
	protected ISimulationPolicy simulationPolicy;
	protected IPropagationPolicy propagationPolicy;
	protected IBudgetManager budgetManager;
    
	@Inject
    public MCTS(ITree tree, 
    		ISelectionPolicy selectionPolicy, IExpansionPolicy expansionPolicy, ISimulationPolicy simulationPolicy, IPropagationPolicy propagationPolicy, 
    		IBudgetManager budgetManager) {
		super.setStructure(tree);
		this.selectionPolicy = selectionPolicy;
		this.expansionPolicy = expansionPolicy;
		this.simulationPolicy = simulationPolicy;
		this.propagationPolicy = propagationPolicy;
		this.budgetManager = budgetManager;
    }
	
	@Override
	public void run(INode node) {
        while (!node.getState().getSituation().hasFinished()) {
            node = this.mcts(node); 
        }
	}
                    
    protected INode mcts(INode node) {     
    	for (var i = 1; i < Integer.MAX_VALUE; i++) {       	
            // Phase 1 - Selection
            var promisingNode = selection(node);
            
            // Phase 2 - Expansion
            if ((promisingNode.getState().getVisitCount() >= 1)||(promisingNode.equals(super.getStructure().getFirst())))
            	expansion(promisingNode); //Only expand it if it is the root node or it has already been visited yet

            // Phase 3 - Simulation
            var nodeToExplore = promisingNode;
            if (promisingNode.getSuccessors().size() > 0) {
                nodeToExplore = promisingNode.getRandomSuccessorNode();
            }
            var result = simulation(nodeToExplore);
            
            // Phase 4 - Update
            propagation(nodeToExplore, result);
   
            super.structureChangedEvent(super.getStructure(), node, nodeToExplore, super.getMovementNumber(), i, result);
            super.pauseEvent(super.getStructure());
            if (budgetManager.checkStopCondition(i, super.getTimer())) break; 
        }

        var winnerNode = node.getSuccessorWithMaxValue(node.getState().getParticipantManager().getOpponent());
        
        super.movementPerformedEvent(super.getStructure(), node, winnerNode, super.getMovementNumber());
        super.pauseEvent(super.getStructure());
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