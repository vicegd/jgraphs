package jgraphs.app.tictactoe;

import jgraphs.algorithm.mcts.budget.DefaultBudgetManager;
import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.expansion.GenerateAllExpansionPolicy;
import jgraphs.algorithm.mcts.expansion.IExpansionPolicy;
import jgraphs.algorithm.mcts.propagation.IPropagationPolicy;
import jgraphs.algorithm.mcts.propagation.UpdateAllPropagationPolicy;
import jgraphs.algorithm.mcts.selection.ISelectionPolicy;
import jgraphs.algorithm.mcts.selection.UCBSelectionPolicy;
import jgraphs.algorithm.mcts.simulation.ISimulationPolicy;
import jgraphs.algorithm.mcts.simulation.RandomMovementSimulationPolicy;
import jgraphs.core.node.IMaxValueNode;
import jgraphs.core.node.ScoreMaxValueNode;
import jgraphs.core.participant.IParticipantManager;
import jgraphs.core.participant.TwoParticipantsManager;
import jgraphs.utils.module.DefaultModuleConfiguration;
import jgraphs.utils.module.EModuleConfiguration;

public class TicTacToeModule extends DefaultModuleConfiguration {
	EModuleConfiguration moduleConfiguration;
	
	public TicTacToeModule(EModuleConfiguration moduleConfiguration) {
		super(moduleConfiguration);
		this.moduleConfiguration = moduleConfiguration;
	}
	
    @Override
    protected void configure() {
    	super.configure();    
    	switch (this.moduleConfiguration) {
	    	case BASIC:
	    	case SILENTBASIC:
	    	case PBASIC:
	    	case SILENTPBASIC:
	    		break;
    	}
    	bind(ISelectionPolicy.class).to(UCBSelectionPolicy.class);
    	bind(IExpansionPolicy.class).to(GenerateAllExpansionPolicy.class);
    	bind(ISimulationPolicy.class).to(RandomMovementSimulationPolicy.class);
    	bind(IPropagationPolicy.class).to(UpdateAllPropagationPolicy.class);
    	bind(IBudgetManager.class).to(DefaultBudgetManager.class);
    	bind(IParticipantManager.class).to(TwoParticipantsManager.class);
    	bind(IMaxValueNode.class).to(ScoreMaxValueNode.class);
    }

}