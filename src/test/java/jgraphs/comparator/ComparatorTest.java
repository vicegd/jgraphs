package jgraphs.comparator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSerializer;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.subsystem.comparator.DefaultComparator;
import jgraphs.subsystem.comparator.IComparator;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class ComparatorTest {
    private BacktrackingAll backtracking;
    private BacktrackingAll backtracking2;
    private IComparator comparator;
        
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule(EModuleConfiguration.BASIC)).getInjector(BacktrackingAll.class);
        this.backtracking2 = Dependency.getInstance(new PermutationModule(EModuleConfiguration.BASIC)).getInjector(BacktrackingAll.class);
        this.comparator = new DefaultComparator();
    }
        
    @Test
    public void givenSameStructureWith2Nodes_comparator_then0Differences() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();  
            
        var comparison = this.comparator.compare(tree, tree, new PermutationSerializer());
        assertEquals(0, comparison.toList().size());
    }
    
    @Test
    public void given2StructuresWith2Nodes_comparator_then7Differences() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();  
        
    	var tree2 = this.backtracking2.getStructure();
    	tree2.setFirstSituation(new PermutationSituation(1));
        this.backtracking2.run();   

        var comparison = this.comparator.compare(tree, tree2, new PermutationSerializer(), "s3.json");
        assertEquals(6, comparison.toList().size());
    }
    
}
