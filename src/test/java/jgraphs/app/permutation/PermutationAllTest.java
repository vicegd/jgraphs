package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.traceability.BasicTraceability;
import jgraphs.utils.Dependency;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;

public class PermutationAllTest {
    private BacktrackingAll backtracking;

    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
        this.backtracking.addStatistic(new TreeConsoleStatistic());
        this.backtracking.addVisualizer(new SimpleConsoleVisualizer());
        this.backtracking.addTraceability(new BasicTraceability());
    }
    
    //@Test
    public void givenInitState_whenGetAllPossiblePermutations1Elements_then1Results() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());         
        assertEquals(1, this.backtracking.getResults().size());
    }

    //@Test
    public void givenInitState_whenGetAllPossiblePermutations4Elements_then24Results() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());        
        assertEquals(24, this.backtracking.getResults().size());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations5Elements_then120Results() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(5));
        this.backtracking.execute(tree.getFirst());       
        assertEquals(120, this.backtracking.getResults().size());
    }

}
