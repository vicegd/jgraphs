package jgraphs.app.tictactoe;

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
        //  this.mcts.addStatistic(new TreeConsoleStatistic());
        //   this.mcts.addVisualizer(new SimpleConsoleVisualizer());
           //this.mcts.addVisualizer(new SimpleGraphVisualizer());
           //this.mcts.addVisualizer(new GraphVisualizer());
          // this.mcts.addTraceability(new DefaultTraceability());
          // this.mcts.addVisualizer(new ShapeGraphVisualizer());
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
