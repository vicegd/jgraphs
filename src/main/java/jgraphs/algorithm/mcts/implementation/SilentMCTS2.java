package jgraphs.algorithm.mcts.implementation;

import com.google.inject.Inject;

import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.expansion.IExpansionPolicy;
import jgraphs.algorithm.mcts.propagation.IPropagationPolicy;
import jgraphs.algorithm.mcts.selection.ISelectionPolicy;
import jgraphs.algorithm.mcts.simulation.ISimulationPolicy;
import jgraphs.core.node.INode;
import jgraphs.core.structure.tree.ITree;

public class SilentMCTS2 extends MCTS {   
	@Inject
    public SilentMCTS2(ITree tree, 
    		ISelectionPolicy selectionPolicy, IExpansionPolicy expansionPolicy, ISimulationPolicy simulationPolicy, IPropagationPolicy propagationPolicy,
    		IBudgetManager budgetManager) {
		super(tree, selectionPolicy, expansionPolicy, simulationPolicy, propagationPolicy, budgetManager);
    }
	
	@Override
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
   
            if (budgetManager.checkStopCondition(i)) break; 
        }

        var winnerNode = node.getSuccessorWithMaxValue(node.getState().getParticipantManager().getOpponent());
        
        if (winnerNode.getState().getSituation().checkStatus() != -1) {
        	super.addResult(winnerNode);	       	
        }
        
        return winnerNode;
    }
    
    @Override
    protected void expansion(INode promisingNode) {
	    expansionPolicy.expansion(promisingNode);
    }   
}