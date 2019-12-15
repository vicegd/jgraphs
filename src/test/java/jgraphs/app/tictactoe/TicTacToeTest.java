package jgraphs.app.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import jgraphs.algorithm.mcts.MCTS;
import jgraphs.logging.Logging;
import jgraphs.profiling.Profiling;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.utils.Dependency;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;

public class TicTacToeTest {
	protected static final Logger log = Logging.getInstance().getLogger(TicTacToeTest.class);
	protected static final Profiling profiling = Profiling.getInstance();
    private MCTS mcts;

    @BeforeClass
    public static void beforeClass() {
    	profiling.createAndActivate(TicTacToeTest.class);
    }
    
    
    @Before	
    public void initialize() {
        this.mcts = Dependency.getInstance(new TicTacToeModule()).getInjector().getInstance(MCTS.class);
        this.mcts.getStructure().setFirstSituation(new TicTacToeSituation());
        this.mcts.addStatistic(new TreeConsoleStatistic());
        this.mcts.addVisualizer(new SimpleConsoleVisualizer());
       // this.mcts.addVisualizer(new SimpleGraphVisualizer());
       // this.mcts.addVisualizer(new GraphVisualizer());
       // this.mcts.addVisualizer(new ShapeGraphVisualizer());
    }

    @Test
    public void givenInitBoardState_whenGetAllPossibleStates_then9ElementsList() {
        var initState = this.mcts.getStructure().getFirst().getState();
        var possibleStates = initState.nextStates();
        assertEquals(9, possibleStates.size());
    }

    @Test
    public void givenEmptyBoard_whenPerformMove_thenLessAvailablePossitions() {
        var situation = new TicTacToeSituation();
        var initAvailablePositions = situation.nextSituations();
        assertEquals(9, initAvailablePositions.size());
        
        var situation2 = situation.nextSituations().get(0);
        var availablePositions = situation2.nextSituations();
        assertEquals(8, availablePositions.size());
    }
   
    @Test
    public void givenEmptyBoard_trainingP1_P1Wins() {
        this.mcts.getBudgetManager().setIterations(10000);
        this.mcts.setTrainers(new boolean[] {true, false});
        this.mcts.execute(this.mcts.getStructure().getFirst()); 
        
        var status = this.mcts.getFirstResult().getState().getSituation().checkStatus();
        assertTrue(status == 1);
    }
    
    @AfterClass
    public static void afterClass() {
        profiling.stop();
        log.info(profiling.toString());
    }

}
