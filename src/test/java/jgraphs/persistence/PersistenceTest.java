package jgraphs.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSerializer;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.utils.Dependency;

public class PersistenceTest {
    private BacktrackingAll backtracking;
    private MemoryPersistence memoryPersistence = new MemoryPersistence();
    private FilePersistence filePersistence = new FilePersistence();
    private H2Persistence H2Persistence = new H2Persistence();
    
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
    }
        
    @Test
    public void givenStructure_memoryPersistenceSave2Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        memoryPersistence.saveStructure("s2", null, tree);   
        var newTree = memoryPersistence.loadStructure("s2", null);  
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_memoryPersistenceSave65Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());   
    
        memoryPersistence.saveStructure("s65", null, tree); 
        var newTree = memoryPersistence.loadStructure("s65", null);
        assertEquals(65, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_filePersistenceSave2Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        filePersistence.saveStructure("s2", new PermutationSerializer(), tree);   
        var newTree = filePersistence.loadStructure("s2", new PermutationSerializer());  
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_H2PersistenceSave2Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        H2Persistence.saveStructure("s2", new PermutationSerializer(), tree);   
        var newTree = H2Persistence.loadStructure("s2", new PermutationSerializer());  
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    } 
}
