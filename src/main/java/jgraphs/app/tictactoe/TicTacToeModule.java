package jgraphs.app.tictactoe;

import jgraphs.algorithm.mcts.budget.DefaultBudgetManager;
import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.defaultpolicy.IDefaultPolicy;
import jgraphs.algorithm.mcts.defaultpolicy.RandomMovement;
import jgraphs.algorithm.mcts.treepolicy.ITreePolicy;
import jgraphs.algorithm.mcts.treepolicy.UCB;
import jgraphs.core.participant.IParticipantManager;
import jgraphs.core.participant.TwoParticipantsManager;
import jgraphs.core.utils.BasicModule;

public class TicTacToeModule extends BasicModule {
    @Override
    protected void configure() {
    	super.configure();
    	bind(ITreePolicy.class).to(UCB.class);
    	bind(IDefaultPolicy.class).to(RandomMovement.class);
    	bind(IBudgetManager.class).to(DefaultBudgetManager.class);
    	bind(IParticipantManager.class).to(TwoParticipantsManager.class);
    }
}