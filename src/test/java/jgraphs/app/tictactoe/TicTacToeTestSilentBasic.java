package jgraphs.app.tictactoe;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jgraphs.algorithm.mcts.implementation.SilentMCTS;
import jgraphs.logger.DefaultLogger;
import jgraphs.logger.ILogger;
import jgraphs.profiler.DefaultProfiler;
import jgraphs.profiler.IProfiler;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class TicTacToeTestSilentBasic {
	private static final ILogger logger = new DefaultLogger(TicTacToeTestSilentBasic.class);
	private static final IProfiler profiler = new DefaultProfiler(TicTacToeTestSilentBasic.class);
    private SilentMCTS mcts;

    @BeforeClass
    public static void beforeClass() {
    	profiler.create();
    }
    
    @Before	
    public void initialize() {
        this.mcts = Dependency.getInstance(new TicTacToeModule(EModuleConfiguration.SILENTBASIC)).getInjector().getInstance(SilentMCTS.class);
        this.mcts.getStructure().setFirstSituation(new TicTacToeSituation(11));
    }
  
    @Test
    public void givenEmptyBoard_trainingP1_P1Wins() {
    	profiler.start("trainingP1");
        this.mcts.getBudgetManager().setIterations(80);
        this.mcts.setTrainers(new boolean[] {true, false});
        this.mcts.execute(this.mcts.getStructure().getFirst()); 
        
        var status = this.mcts.getFirstResult().getState().getSituation().checkStatus();
        assertTrue(status == 1);
    }
  
    @AfterClass
    public static void afterClass() {
        profiler.stop();
        logger.info(profiler.toString());
    }

}
