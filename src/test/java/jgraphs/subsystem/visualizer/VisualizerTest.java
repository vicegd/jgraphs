package jgraphs.subsystem.visualizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSerializer;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.subsystem.persistence.FilePersistence;
import jgraphs.subsystem.persistence.H2Persistence;
import jgraphs.subsystem.persistence.MemoryPersistence;
import jgraphs.subsystem.visualizer.console.SimpleConsoleVisualizer;
import jgraphs.subsystem.visualizer.graph.GraphVisualizer;
import jgraphs.subsystem.visualizer.graph.ShapeGraphVisualizer;
import jgraphs.subsystem.visualizer.graph.SimpleGraphVisualizer;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class VisualizerTest {
    private BacktrackingAll backtracking;
    private SimpleConsoleVisualizer simpleConsoleVisualizer;
    private SimpleGraphVisualizer simpleGraphVisualizer;
    private ShapeGraphVisualizer shapeGraphVisualizer;
    private GraphVisualizer graphVisualizer;
        
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule(EModuleConfiguration.BASIC)).getInjector(BacktrackingAll.class);
        this.simpleConsoleVisualizer = new SimpleConsoleVisualizer();
        this.simpleGraphVisualizer = new SimpleGraphVisualizer();
        this.shapeGraphVisualizer = new ShapeGraphVisualizer();
        this.graphVisualizer = new GraphVisualizer();
        this.backtracking.addVisualizer(this.simpleConsoleVisualizer);
        //this.backtracking.addVisualizer(this.simpleGraphVisualizer);
        //this.backtracking.addVisualizer(this.shapeGraphVisualizer);
        //this.backtracking.addVisualizer(this.graphVisualizer);
    }
        
    @Test
    public void givenStructure_memoryPersistenceSave2Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();   
    
        memoryPersistence.saveStructure("s2", null, tree);   
        var newTree = memoryPersistence.loadStructure("s2", null);  
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_memoryPersistenceSave65Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.run();   
    
        memoryPersistence.saveStructure("s65", null, tree); 
        var newTree = memoryPersistence.loadStructure("s65", null);
        assertEquals(65, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_filePersistenceSave2Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();   
    
        filePersistence.saveStructure("s2", new PermutationSerializer(), tree);   
        var newTree = filePersistence.loadStructure("s2", new PermutationSerializer());  
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_H2PersistenceSave2Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();   
    
        H2Persistence.saveStructure("s2", new PermutationSerializer(), tree);   
        var newTree = H2Persistence.loadStructure("s2", new PermutationSerializer());  
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    } 
 
}
