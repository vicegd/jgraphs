package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.logging.Logging;
import jgraphs.profiling.Profiling;
import jgraphs.statistics.TreeConsoleStatistic;
import jgraphs.traceability.DefaultTraceability;
import jgraphs.utils.Dependency;
import jgraphs.visualizer.console.SimpleConsoleVisualizer;

public class PermutationAllTest {
	protected static final Logger log = Logging.getInstance().getLogger(PermutationAllTest.class);
	protected static final Profiling profiling = Profiling.getInstance();
    private BacktrackingAll backtracking;

    @BeforeClass
    public static void beforeClass() {
    	profiling.createAndActivate(PermutationAllTest.class);
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
    	profiling.start(PermutationAllTest.class, "1Element");
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());         
        assertEquals(1, this.backtracking.getResults().size());
    }

    @Test
    public void givenInitState_whenGetAllPossiblePermutations4Elements_then24Results() {
    	profiling.start(PermutationAllTest.class, "4Elements");
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());        
        assertEquals(24, this.backtracking.getResults().size());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations5Elements_then120Results() {
    	profiling.start(PermutationAllTest.class, "5Elements");
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(5));
        this.backtracking.execute(tree.getFirst());       
        assertEquals(120, this.backtracking.getResults().size());
    }
    
    @AfterClass
    public static void afterClass() {
        profiling.stop();
        log.info(profiling.toString());
    }

}
