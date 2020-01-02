package jgraphs.app.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jgraphs.algorithm.mcts.implementation.MCTS;
import jgraphs.logger.DefaultLogger;
import jgraphs.logger.ILogger;
import jgraphs.profiler.DefaultProfiler;
import jgraphs.profiler.IProfiler;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;

public class TicTacToeTestBasic {
	private static final ILogger logger = new DefaultLogger(TicTacToeTestBasic.class);
	private static final IProfiler profiler = new DefaultProfiler(TicTacToeTestBasic.class);
    private MCTS mcts;

    @BeforeClass
    public static void beforeClass() {
    	profiler.create();
    }
    
    @Before	
    public void initialize() {
        this.mcts = Dependency.getInstance(new TicTacToeModule(EModuleConfiguration.BASIC)).getInjector().getInstance(MCTS.class);
        this.mcts.getStructure().setFirstSituation(new TicTacToeSituation(11));
      //  this.mcts.addStatistic(new TreeConsoleStatistic());
     //   this.mcts.addVisualizer(new SimpleConsoleVisualizer());
        //this.mcts.addVisualizer(new SimpleGraphVisualizer());
        //this.mcts.addVisualizer(new GraphVisualizer());
       // this.mcts.addTraceability(new DefaultTraceability());
       // this.mcts.addVisualizer(new ShapeGraphVisualizer());
    }

    @Test
    public void givenInitBoardState_whenGetAllPossibleStates_then9ElementsList() {
    	profiler.start("allPossibleStates");
        var initState = this.mcts.getStructure().getFirst().getState();
        var possibleStates = initState.nextStates();
        assertEquals(9, possibleStates.size());
    }

    @Test
    public void givenEmptyBoard_whenPerformMove_thenLessAvailablePossitions() {
    	profiler.start("allPossiblesSituations");
        var situation = new TicTacToeSituation();
        var initAvailablePositions = situation.nextSituations();
        assertEquals(9, initAvailablePositions.size());
        
        var situation2 = situation.nextSituations().get(0);
        var availablePositions = situation2.nextSituations();
        assertEquals(8, availablePositions.size());
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
