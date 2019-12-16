package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.logger.ILogger;
import jgraphs.logger.DefaultLogger;
import jgraphs.profiler.DefaultProfiler;
import jgraphs.profiler.IProfiler;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.traceability.DefaultTraceability;
import jgraphs.utils.Dependency;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;

public class PermutationAllTest {
	private static final ILogger logger = new DefaultLogger(PermutationAllTest.class);
	private static final IProfiler profiler = new DefaultProfiler(PermutationAllTest.class);
    private BacktrackingAll backtracking;

    @BeforeClass
    public static void beforeClass() {
    	profiler.create();
    }
    
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
        this.backtracking.addStatistic(new TreeConsoleStatistic());
        this.backtracking.addVisualizer(new SimpleConsoleVisualizer());
        this.backtracking.addTraceability(new DefaultTraceability());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations1Elements_then1Results() {
    	profiler.start("1Element");
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());         
        assertEquals(1, this.backtracking.getResults().size());
    }

    @Test
    public void givenInitState_whenGetAllPossiblePermutations4Elements_then24Results() {
    	profiler.start("4Elements");
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());        
        assertEquals(24, this.backtracking.getResults().size());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations5Elements_then120Results() {
    	profiler.start("5Elements");
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(5));
        this.backtracking.execute(tree.getFirst());       
        assertEquals(120, this.backtracking.getResults().size());
    }
    
    @AfterClass
    public static void afterClass() {
        profiler.stop();
        logger.info(profiler.toString());
    }

}
