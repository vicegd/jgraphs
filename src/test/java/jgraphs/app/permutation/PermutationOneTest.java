package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingOne;
import jgraphs.subsystem.statistics.TreeConsoleStatistics;
import jgraphs.subsystem.visualizer.console.SimpleConsoleVisualizer;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class PermutationOneTest {
    private BacktrackingOne backtracking;

    @Before	
    public void initialize() {    	
        this.backtracking = Dependency.getInstance(new PermutationModule(EModuleConfiguration.BASIC)).getInjector(BacktrackingOne.class);
        this.backtracking.addStatistics(new TreeConsoleStatistics());
        this.backtracking.addVisualizer(new SimpleConsoleVisualizer());
    }
    
    @Test
    public void givenInitState_whenGetAllPossiblePermutations1Elements_then1Result() { 
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();               
        assertEquals(1, this.backtracking.getResults().size());
    }

    @Test
    public void givenInitState_whenGetAllPossiblePermutations3Elements_then1Result() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(3));
        this.backtracking.run();              
        assertEquals(1, this.backtracking.getResults().size());
    }
}
