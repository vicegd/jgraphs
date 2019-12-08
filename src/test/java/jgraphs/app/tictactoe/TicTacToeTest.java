package jgraphs.app.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.mcts.MCTS;
import jgraphs.core.utils.Utils;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;
import jgraphs.visualizer.graph.SimpleGraphVisualizer;

public class TicTacToeTest {
    private MCTS mcts;

    @Before	
    public void initGameTree() {
        this.mcts = Utils.getInstance(new TicTacToeModule()).getInjector().getInstance(MCTS.class);
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
        var node = this.mcts.getStructure().getFirst();
        //this.mcts.getBudgetManager().setIterations(500);
        //this.mcts.setTrainers(new boolean[] {true, false});
   
        for (int i = 0; i < 9; i++) {
            this.mcts.execute(node); 
            if (this.mcts.getResult().size() > 0) {
            	node = this.mcts.getResult().get(0);
            	break;
            }
        }
        
        var winStatus = node.getState().getSituation().checkStatus();
        assertTrue(winStatus == 1);
    }

}
