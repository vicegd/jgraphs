package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingOne;
import jgraphs.logger.ILogger;
import jgraphs.logger.DefaultLogger;
import jgraphs.profiler.DefaultProfiler;
import jgraphs.profiler.IProfiler;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.utils.Dependency;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;

public class PermutationOneTest {
	private static final ILogger logger = new DefaultLogger(PermutationOneTest.class);
	private static final IProfiler profiler = new DefaultProfiler(PermutationOneTest.class);
    private BacktrackingOne backtracking;

    @BeforeClass
    public static void beforeClass() {
    	profiler.create();
    }
    
    @Before	
    public void initialize() {    	
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingOne.class);
        this.backtracking.addStatistic(new TreeConsoleStatistic());
        this.backtracking.addVisualizer(new SimpleConsoleVisualizer());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations1Elements_then1Result() { 
    	profiler.start("1Element");
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());              
        assertEquals(1, this.backtracking.getResults().size());
    }

    @Test
    public void givenInitState_whenGetAllPossiblePermutations3Elements_then1Result() {
    	profiler.start("3Elements");
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(3));
        this.backtracking.execute(tree.getFirst());              
        assertEquals(1, this.backtracking.getResults().size());
    }
    
    @AfterClass
    public static void afterClass() {
        profiler.stop();
        logger.info(profiler.toString());
    }

}
