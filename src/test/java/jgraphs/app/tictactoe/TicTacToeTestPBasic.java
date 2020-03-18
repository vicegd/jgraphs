package jgraphs.app.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jgraphs.algorithm.mcts.implementation.MCTS;
import jgraphs.algorithm.mcts.implementation.PMCTS;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.subsystem.profiler.DefaultProfiler;
import jgraphs.subsystem.profiler.IProfiler;
import jgraphs.subsystem.statistics.TreeConsoleStatistic;
import jgraphs.subsystem.visualizer.console.SimpleConsoleVisualizer;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class TicTacToeTestPBasic {
	private static final ILogger logger = new DefaultLogger(TicTacToeTestPBasic.class);
    private PMCTS mcts;
   
    @Before	
    public void initialize() {
        this.mcts = Dependency.getInstance(new TicTacToeModule(EModuleConfiguration.PBASIC)).getInjector(PMCTS.class);
        this.mcts.getStructure().setFirstSituation(new TicTacToeSituation(5));
      //  this.mcts.addStatistic(new TreeConsoleStatistic());
      //  this.mcts.addVisualizer(new SimpleConsoleVisualizer());
        //this.mcts.addVisualizer(new SimpleGraphVisualizer());
        //this.mcts.addVisualizer(new GraphVisualizer());
       // this.mcts.addTraceability(new DefaultTraceability());
       // this.mcts.addVisualizer(new ShapeGraphVisualizer());
    }
  
    @Test
    public void givenEmptyBoard_trainingP1_P1Wins() {
        this.mcts.getBudgetManager().setIterations(500);
        this.mcts.setTrainers(new boolean[] {true, false});
        this.mcts.execute(this.mcts.getStructure().getFirst()); 
        
        var status = this.mcts.getFirstResult().getState().getSituation().checkStatus();
        assertTrue(status == 1);
    }

}
