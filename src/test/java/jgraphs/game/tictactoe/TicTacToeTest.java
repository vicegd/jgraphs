package jgraphs.game.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.mcts.MCTS;
import jgraphs.core.situation.Position;
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
        this.mcts.addVisualizer(new SimpleGraphVisualizer());
       // this.mcts.addVisualizer(new GraphVisualizer());
       // this.mcts.addVisualizer(new ShapeGraphVisualizer());
    }

    @Test
    public void givenInitBoardState_whenGetAllPossibleStates_then9ElementsList() {
        var initState = mcts.getTree().getFirst().getState();
        var possibleStates = initState.getAllPossibleStates();
        assertEquals(9, possibleStates.size());
    }

    @Test
    public void givenEmptyBoard_whenPerformMove_thenLessAvailablePossitions() {
        var board = new TicTacToeBoard();
        var initAvailablePositions = board.getEmptyPositions().size();
        assertEquals(9, initAvailablePositions);
        
        board.performMovement(1, new Position(1, 1));
        var availablePositions = board.getEmptyPositions().size();
        assertEquals(8, availablePositions);
    }
   
    @Test
    public void givenEmptyBoard_trainingP1_P1Wins() {
        var node = mcts.getTree().getFirst();
        //this.mcts.getBudgetManager().setIterations(500);
        //this.mcts.setTrainers(new boolean[] {true, false});
   
        for (int i = 0; i < 9; i++) {
            node = mcts.findNextMove(node); 
            if (node.getState().getBoard().checkStatus() != -1) { 
                break;
            }
        }
        
        var winStatus = node.getState().getBoard().checkStatus();
        assertTrue(winStatus == 1);
    }

}
