package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.algorithm.backtracking.BacktrackingOne;
import jgraphs.core.utils.Utils;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;

public class PermutationOneTest {
	protected static Logger log = LoggerFactory.getLogger(PermutationOneTest.class);
    private BacktrackingOne backtracking;

    @Before	
    public void initialize() {
        this.backtracking = Utils.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingOne.class);
        this.backtracking.addStatistic(new TreeConsoleStatistic());
        this.backtracking.addVisualizer(new SimpleConsoleVisualizer());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations1Elements_then1Result() {
        var node = this.backtracking.getStructure().getFirst();
        var permutationSituation = (PermutationSituation)node.getState().getSituation();
        permutationSituation.setSize(1); 
        this.backtracking.execute(node);        
        assertEquals(1, this.backtracking.getResults().size());
    }

    @Test
    public void givenInitState_whenGetAllPossiblePermutations3Elements_then1Result() {
        var node = this.backtracking.getStructure().getFirst();
        var permutationSituation = (PermutationSituation)node.getState().getSituation();
        permutationSituation.setSize(3); 
        this.backtracking.execute(node);        
        assertEquals(1, this.backtracking.getResults().size());
    }

}
