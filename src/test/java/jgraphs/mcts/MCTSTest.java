package jgraphs.mcts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;

import jgraphs.algorithm.mcts.MCTS;
import jgraphs.algorithm.mcts.UCB;
import jgraphs.core.board.Position;
import jgraphs.core.status.EGameStatus;
import jgraphs.core.status.EPlayer;
import jgraphs.core.tree.ITree;
import jgraphs.core.utils.BasicModule;
import jgraphs.core.utils.Utils;
import jgraphs.game.tictactoe.TicTacToeBoard;
import jgraphs.visualizers.ConsoleVisualizer;
import jgraphs.visualizers.GraphVisualizer;

public class MCTSTest {
    ITree tree;
    MCTS mcts;

    @Before	
    public void initGameTree() {
    	this.tree = Utils.getInstance().getInjector().getInstance(ITree.class);
        
        this.mcts = new MCTS(tree);
        this.mcts.addVisualizer(new ConsoleVisualizer());
        this.mcts.addVisualizer(new GraphVisualizer());
    }

    @Test
    public void givenInitBoardState_whenGetAllPossibleStates_then9ElementsList() {
        var initState = tree.getRoot().getState();
        var possibleStates = initState.getAllPossibleStates();
        assertEquals(9, possibleStates.size());
    }

    @Test
    public void givenEmptyBoard_whenPerformMove_thenLessAvailablePossitions() {
        var board = new TicTacToeBoard();
        var initAvailablePositions = board.getEmptyPositions().size();
        assertEquals(9, initAvailablePositions);
        
        board.performMove(EPlayer.P1, new Position(1, 1));
        var availablePositions = board.getEmptyPositions().size();
        assertEquals(8, availablePositions);
    }
   
    @Test
    public void givenEmptyBoard_trainingP1_P1WinsOrDraw() {
        var node = tree.getRoot();
        this.mcts.setTrainP1(true);
        this.mcts.setTrainP2(false);
   
        for (int i = 0; i < 9; i++) {
            node = mcts.findNextMove(node); 
            //System.out.println(tree.getStatistics().numberNodes);
            if (node.getState().getBoard().checkStatus() != EGameStatus.In_Progress) {
                break;
            }
        }
        
        //this.mcts.printPath();
        //System.out.println(tree.getStatistics().numberNodes);
        var winStatus = node.getState().getBoard().checkStatus();
        assertTrue((EGameStatus.P1Won == winStatus) || (EGameStatus.Draw == winStatus));
    }
    
   // @Test
    public void givenEmptyBoard_trainingP2_P2WinsOrDraw() {
        var node = tree.getRoot();
        this.mcts.setTrainP1(false);
        this.mcts.setTrainP2(true);
   
        for (int i = 0; i < 9; i++) {
            node = mcts.findNextMove(node); 
            
            if (node.getState().getBoard().checkStatus() != EGameStatus.In_Progress) {
                break;
            }
        }
        
        this.mcts.printPath();
        var winStatus = node.getState().getBoard().checkStatus();
        assertTrue((EGameStatus.P2Won == winStatus) || (EGameStatus.Draw == winStatus));
    }
    
    @Test
    public void givenStats_whenGetUCTForNode_thenUCTMatchesWithManualData() {
        var ucb = new UCB();
        assertEquals(ucb.getValue(600, 300, 20), 15.79, 0.01);
    }

}
