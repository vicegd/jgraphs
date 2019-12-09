package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.core.utils.Utils;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;

public class PermutationAllTest {
	protected static Logger log = LoggerFactory.getLogger(PermutationAllTest.class);
    private BacktrackingAll backtracking;

    @Before
    public void initialize() {
        this.backtracking = Utils.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
        this.backtracking.addStatistic(new TreeConsoleStatistic());
        this.backtracking.addVisualizer(new SimpleConsoleVisualizer());
    }
    
    //@Test
    public void givenInitState_whenGetAllPossiblePermutations1Elements_then1Results() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituatiton(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());         
        assertEquals(1, this.backtracking.getResults().size());
    }

    @Test
    public void givenInitState_whenGetAllPossiblePermutations4Elements_then24Results() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituatiton(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());        
        assertEquals(24, this.backtracking.getResults().size());
    }
    
    //@Test
    public void givenInitState_whenGetAllPossiblePermutations5Elements_then120Results() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituatiton(new PermutationSituation(5));
        this.backtracking.execute(tree.getFirst());       
        assertEquals(120, this.backtracking.getResults().size());
    }

}
