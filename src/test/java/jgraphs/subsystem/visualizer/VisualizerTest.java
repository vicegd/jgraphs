package jgraphs.subsystem.visualizer;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.subsystem.visualizer.console.SimpleConsoleVisualizer;
import jgraphs.subsystem.visualizer.graph.CompleteGraphVisualizer;
import jgraphs.subsystem.visualizer.graph.FinishedGraphVisualizer;
import jgraphs.subsystem.visualizer.graph.StructureGraphVisualizer;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class VisualizerTest {
    private BacktrackingAll backtracking;
    private SimpleConsoleVisualizer simpleConsoleVisualizer;
    private FinishedGraphVisualizer finishedGraphVisualizer;
    private StructureGraphVisualizer structureGraphVisualizer;
    private CompleteGraphVisualizer completeGraphVisualizer;
        
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule(EModuleConfiguration.BASIC)).getInjector(BacktrackingAll.class);
        this.simpleConsoleVisualizer = new SimpleConsoleVisualizer();
        this.finishedGraphVisualizer = new FinishedGraphVisualizer();
        this.structureGraphVisualizer = new StructureGraphVisualizer();
        this.completeGraphVisualizer = new CompleteGraphVisualizer();
        this.backtracking.addVisualizer(this.simpleConsoleVisualizer);
        this.backtracking.addVisualizer(this.finishedGraphVisualizer);
        this.backtracking.addVisualizer(this.structureGraphVisualizer);
        this.backtracking.addVisualizer(this.completeGraphVisualizer);
    }
        
    @Test
    public void givenStructureWith2Nodes_visualizer_thenViewed() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();   
    }
    
    @Test
    public void givenStructureWith65Nodes_visualizer_thenViewed() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.run();   
    }
 
}
