package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.subsystem.statistics.TreeConsoleStatistics;
import jgraphs.subsystem.traceability.DefaultTraceability;
import jgraphs.subsystem.visualizer.console.SimpleConsoleVisualizer;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class PermutationAllTest {
    private BacktrackingAll backtracking;

    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule(EModuleConfiguration.BASIC)).getInjector(BacktrackingAll.class);
        this.backtracking.addStatistics(new TreeConsoleStatistics());
        this.backtracking.addVisualizer(new SimpleConsoleVisualizer());
        this.backtracking.addTraceability(new DefaultTraceability());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations1Elements_then1Results() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();        
        assertEquals(1, this.backtracking.getResults().size());
    }

    @Test
    public void givenInitState_whenGetAllPossiblePermutations4Elements_then24Results() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.run();         
        assertEquals(24, this.backtracking.getResults().size());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations5Elements_then120Results() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(5));
        this.backtracking.run();     
        assertEquals(120, this.backtracking.getResults().size());
    }
}
