package jgraphs.subsystem.traceability;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class TraceabilityTest {
    private BacktrackingAll backtracking;
    private DefaultTraceability tracebility;
        
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule(EModuleConfiguration.BASIC)).getInjector(BacktrackingAll.class);
        this.tracebility = new DefaultTraceability();
        this.backtracking.addTraceability(this.tracebility);
    }
        
    @Test
    public void givenStructureWith2Nodes_tracebility_thenTrace() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();        
        assertEquals(2, tracebility.getStops());
    }
}
