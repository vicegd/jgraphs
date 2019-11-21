package jgraphs.mcts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.mcts.MCTS;
import jgraphs.algorithm.mcts.treepolicy.UCB;
import jgraphs.core.board.Position;
import jgraphs.core.utils.Utils;
import jgraphs.game.tictactoe.TicTacToeBoard;
import jgraphs.game.tictactoe.TicTacToeModule;
import jgraphs.visualizers.ConsoleVisualizer;
import jgraphs.visualizers.GraphVisualizer;

public class TicTacToeTest {
    private MCTS mcts;

    @Before	
    public void initGameTree() {
        this.mcts = Utils.getInstance(new TicTacToeModule()).getInjector().getInstance(MCTS.class);
        this.mcts.addVisualizer(new ConsoleVisualizer());
        this.mcts.addVisualizer(new GraphVisualizer());
    }

    @Test
    public void givenInitBoardState_whenGetAllPossibleStates_then9ElementsList() {
        var initState = mcts.getTree().getRoot().getState();
        var possibleStates = initState.getAllPossibleStates();
        assertEquals(9, possibleStates.size());
    }

    @Test
    public void givenEmptyBoard_whenPerformMove_thenLessAvailablePossitions() {
        var board = new TicTacToeBoard();
        var initAvailablePositions = board.getEmptyPositions().size();
        assertEquals(9, initAvailablePositions);
        
        board.performMove(1, new Position(1, 1));
        var availablePositions = board.getEmptyPositions().size();
        assertEquals(8, availablePositions);
    }
   
    @Test
    public void givenEmptyBoard_trainingP1_P1WinsOrDraw() {
        var node = mcts.getTree().getRoot();
        this.mcts.setTrainP1(true);
        this.mcts.setTrainP2(false);
   
        for (int i = 0; i < 9; i++) {
            node = mcts.findNextMove(node); 
            //System.out.println(tree.getStatistics().numberNodes);
            if (node.getState().getBoard().checkStatus() != -1) { 
                break;
            }
        }
        
        //this.mcts.printPath();
        //System.out.println(tree.getStatistics().numberNodes);
        var winStatus = node.getState().getBoard().checkStatus();
        assertTrue((winStatus == 1) || (winStatus == 0));
    }
    
   // @Test
    public void givenEmptyBoard_trainingP2_P2WinsOrDraw() {
        var node = mcts.getTree().getRoot();
        this.mcts.setTrainP1(false);
        this.mcts.setTrainP2(true);
   
        for (int i = 0; i < 9; i++) {
            node = mcts.findNextMove(node); 
            
            if (node.getState().getBoard().checkStatus() != -1) {
                break;
            }
        }
        
        var winStatus = node.getState().getBoard().checkStatus();
        assertTrue((winStatus == 2) || (winStatus == 0));
    }
    
    @Test
    public void givenStats_whenGetUCTForNode_thenUCTMatchesWithManualData() {
        var ucb = new UCB();
        assertEquals(ucb.getValue(600, 300, 20), 15.79, 0.01);
    }

}
