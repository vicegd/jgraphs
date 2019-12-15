package jgraphs.comparator;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSerializer;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.logging.Logging;
import jgraphs.profiling.Profiling;
import jgraphs.utils.Dependency;

public class ComparatorTest {
	protected static final Logger log = Logging.getInstance().getLogger(ComparatorTest.class);
	protected static final Profiling profiling = Profiling.getInstance();
    private BacktrackingAll backtracking;
    private BacktrackingAll backtracking2;
    private IComparator comparator;
    
    @BeforeClass
    public static void beforeClass() {
    	profiling.createAndActivate(ComparatorTest.class);
    }
    
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
        this.backtracking2 = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
        this.comparator = new DefaultComparator();
    }
        
    @Test
    public void givenSameStructuresWith2Nodes_comparator_then0Differences() {
    	profiling.start(ComparatorTest.class, "0Differences");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());  
            
        profiling.start(ComparatorTest.class, "comparison");
        var comparison = this.comparator.compare(tree, tree, new PermutationSerializer());
        assertEquals(0, comparison.toList().size());
    }
    
    @Test
    public void given2StructuresWith2Nodes_comparator_then6Differences() {
    	profiling.start(ComparatorTest.class, "6Differences");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());  
        
    	var tree2 = this.backtracking2.getStructure();
    	tree2.setFirstSituation(new PermutationSituation(1));
        this.backtracking2.execute(tree2.getFirst());  
    
        profiling.start(ComparatorTest.class, "comparison");
        var comparison = this.comparator.compare(tree, tree2, new PermutationSerializer());
        assertEquals(6, comparison.toList().size());
    }
    
    @AfterClass
    public static void afterClass() {
        profiling.stop();
        log.info(profiling.toString());
    }
    
}
