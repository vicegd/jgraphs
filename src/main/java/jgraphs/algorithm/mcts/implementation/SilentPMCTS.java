package jgraphs.algorithm.mcts.implementation;

import java.util.Collections;

import com.google.inject.Inject;

import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.defaultpolicy.IDefaultPolicy;
import jgraphs.algorithm.mcts.treepolicy.ITreePolicy;
import jgraphs.core.structure.tree.ITree;

public class SilentPMCTS extends SilentMCTS {   
	@Inject
    public SilentPMCTS(ITree tree, ITreePolicy treePolicy, IDefaultPolicy defaultPolicy,  IBudgetManager budgetManager) {
		super(tree, treePolicy, defaultPolicy, budgetManager);
		super.results = Collections.synchronizedList(super.results); 
    }
    
}