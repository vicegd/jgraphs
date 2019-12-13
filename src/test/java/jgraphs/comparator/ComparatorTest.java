package jgraphs.comparator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSerializer;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.utils.Dependency;

public class ComparatorTest {
    private BacktrackingAll backtracking;
    private BacktrackingAll backtracking2;
    private IComparator comparator;
    
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
        this.backtracking2 = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
        this.comparator = new DefaultComparator();
    }
        
    @Test
    public void givenSameStructuresWith2Nodes_comparator_then0Differences() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());  
            
        var comparison = this.comparator.compare(tree, tree, new PermutationSerializer());
        assertEquals(0, comparison.toList().size());
    }
    
    //@Test
    public void given2StructuresWith2Nodes_comparator_then6Differences() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());  
        
    	var tree2 = this.backtracking2.getStructure();
    	tree2.setFirstSituation(new PermutationSituation(1));
        this.backtracking2.execute(tree2.getFirst());  
    
        var comparison = this.comparator.compare(tree, tree2, new PermutationSerializer());
        assertEquals(6, comparison.toList().size());
    }
    
}
