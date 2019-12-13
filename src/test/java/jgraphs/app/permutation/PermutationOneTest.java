package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingOne;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.utils.Dependency;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;

public class PermutationOneTest {
    private BacktrackingOne backtracking;

    @Before	
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingOne.class);
        this.backtracking.addStatistic(new TreeConsoleStatistic());
        this.backtracking.addVisualizer(new SimpleConsoleVisualizer());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations1Elements_then1Result() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());              
        assertEquals(1, this.backtracking.getResults().size());
    }

    @Test
    public void givenInitState_whenGetAllPossiblePermutations3Elements_then1Result() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(3));
        this.backtracking.execute(tree.getFirst());              
        assertEquals(1, this.backtracking.getResults().size());
    }

}
