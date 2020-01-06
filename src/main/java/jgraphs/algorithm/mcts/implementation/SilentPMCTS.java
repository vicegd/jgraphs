package jgraphs.algorithm.mcts.implementation;

import java.util.Collections;

import com.google.inject.Inject;

import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.expansion.IExpansionPolicy;
import jgraphs.algorithm.mcts.propagation.IPropagationPolicy;
import jgraphs.algorithm.mcts.selection.ISelectionPolicy;
import jgraphs.algorithm.mcts.simulation.ISimulationPolicy;
import jgraphs.core.structure.tree.ITree;

public class SilentPMCTS extends SilentMCTS {   
	@Inject
    public SilentPMCTS(ITree tree, 
    		ISelectionPolicy selectionPolicy, IExpansionPolicy expansionPolicy, ISimulationPolicy simulationPolicy, IPropagationPolicy propagationPolicy, 
    		IBudgetManager budgetManager) {
		super(tree, selectionPolicy, expansionPolicy, simulationPolicy, propagationPolicy, budgetManager);
		super.results = Collections.synchronizedList(super.results); 
    }
    
}