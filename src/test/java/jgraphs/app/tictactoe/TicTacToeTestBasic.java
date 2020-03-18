package jgraphs.app.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.mcts.implementation.MCTS;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class TicTacToeTestBasic {
    private MCTS mcts;
   
    @Before	
    public void initialize() {
        this.mcts = Dependency.getInstance(new TicTacToeModule(EModuleConfiguration.BASIC)).getInjector(MCTS.class);
        this.mcts.getStructure().setFirstSituation(new TicTacToeSituation(5));
    }

    @Test
    public void givenInitBoardState_whenGetAllPossibleStates_then9ElementsList() {
        var initState = this.mcts.getStructure().getFirst().getState();
        var possibleStates = initState.nextStates();
        assertEquals(25, possibleStates.size());
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
        this.mcts.getBudgetManager().setIterations(500);
        this.mcts.setTrainers(new boolean[] {true, false});
        this.mcts.execute(this.mcts.getStructure().getFirst()); 
        
        var status = this.mcts.getFirstResult().getState().getSituation().checkStatus();
        assertTrue(status == 1);
    }

}
