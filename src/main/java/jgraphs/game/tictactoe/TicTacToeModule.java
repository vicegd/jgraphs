package jgraphs.game.tictactoe;

import jgraphs.algorithm.mcts.budget.DefaultBudgetManager;
import jgraphs.algorithm.mcts.budget.IBudgetManager;
import jgraphs.algorithm.mcts.defaultpolicy.IDefaultPolicy;
import jgraphs.algorithm.mcts.defaultpolicy.RandomMovement;
import jgraphs.algorithm.mcts.treepolicy.ITreePolicy;
import jgraphs.algorithm.mcts.treepolicy.UCB;
import jgraphs.core.board.IBoard;
import jgraphs.core.player.IPlayerManager;
import jgraphs.core.player.TwoPlayerManager;
import jgraphs.core.utils.BasicModule;
import jgraphs.statistics.ConsoleStatistic;
import jgraphs.statistics.IStatistic;

public class TicTacToeModule extends BasicModule {
    @Override
    protected void configure() {
    	super.configure();
    	bind(IPlayerManager.class).to(TwoPlayerManager.class);
    	bind(ITreePolicy.class).to(UCB.class);
    	bind(IDefaultPolicy.class).to(RandomMovement.class);
    	bind(IStatistic.class).to(ConsoleStatistic.class);
    	bind(IBudgetManager.class).to(DefaultBudgetManager.class);
    	bind(IBoard.class).to(TicTacToeBoard.class);
    }
}