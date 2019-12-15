package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import jgraphs.algorithm.backtracking.BacktrackingOne;
import jgraphs.logging.Logging;
import jgraphs.profiling.Profiling;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.utils.Dependency;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;

public class PermutationOneTest {
	protected static final Logger log = Logging.getInstance().getLogger(PermutationOneTest.class);
	protected static final Profiling profiling = Profiling.getInstance();
    private BacktrackingOne backtracking;

    @BeforeClass
    public static void beforeClass() {
    	profiling.createAndActivate(PermutationOneTest.class);
    }
    
    @Before	
    public void initialize() {    	
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingOne.class);
        this.backtracking.addStatistic(new TreeConsoleStatistic());
        this.backtracking.addVisualizer(new SimpleConsoleVisualizer());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations1Elements_then1Result() { 
    	profiling.start(PermutationOneTest.class, "1Element");
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());              
        assertEquals(1, this.backtracking.getResults().size());
    }

    @Test
    public void givenInitState_whenGetAllPossiblePermutations3Elements_then1Result() {
    	profiling.start(PermutationOneTest.class, "3Elements");
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(3));
        this.backtracking.execute(tree.getFirst());              
        assertEquals(1, this.backtracking.getResults().size());
    }
    
    @AfterClass
    public static void afterClass() {
        profiling.stop();
        log.info(profiling.toString());
    }

}
