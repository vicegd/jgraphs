package jgraphs.app.tictactoe;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.mcts.implementation.PMCTS;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class TicTacToeTestPBasic {
    private PMCTS mcts;
   
    @Before	
    public void initialize() {
        this.mcts = Dependency.getInstance(new TicTacToeModule(EModuleConfiguration.PBASIC)).getInjector(PMCTS.class);
        this.mcts.getStructure().setFirstSituation(new TicTacToePSituation(5));
    }
  
    @Test
    public void givenEmptyBoard_trainingP1_P1Wins() {
        this.mcts.getBudgetManager().setIterations(500);
        this.mcts.setTrainers(new boolean[] {true, false});
        this.mcts.run(); 
        
        var status = this.mcts.getFirstResult().getState().getSituation().checkStatus();
        assertTrue(status == 1);
    }

}
