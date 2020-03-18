package jgraphs.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSerializer;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.subsystem.persistence.FilePersistence;
import jgraphs.subsystem.persistence.H2Persistence;
import jgraphs.subsystem.persistence.MemoryPersistence;
import jgraphs.subsystem.profiler.DefaultProfiler;
import jgraphs.subsystem.profiler.IProfiler;
import jgraphs.utils.Dependency;

public class PersistenceTest {
	private static final ILogger logger = new DefaultLogger(PersistenceTest.class);
	private static final IProfiler profiler = new DefaultProfiler(PersistenceTest.class);
    private BacktrackingAll backtracking;
    private MemoryPersistence memoryPersistence = new MemoryPersistence();
    private FilePersistence filePersistence = new FilePersistence();
    private H2Persistence H2Persistence = new H2Persistence();
    
    @BeforeClass
    public static void beforeClass() {
    	profiler.create();
    }
    
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
    }
        
    @Test
    public void givenStructure_memoryPersistenceSave2Nodes_thenPersisted() {
    	profiler.start("memoryPersistence2Nodes");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        profiler.start("saveStructure");
        memoryPersistence.saveStructure("s2", null, tree);   
        profiler.start("loadStructure");
        var newTree = memoryPersistence.loadStructure("s2", null);  
        profiler.start("compareStructure");
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_memoryPersistenceSave65Nodes_thenPersisted() {
    	profiler.start("memoryPersistence65Nodes");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());   
    
        profiler.start("saveStructure");
        memoryPersistence.saveStructure("s65", null, tree); 
        profiler.start("loadStructure");
        var newTree = memoryPersistence.loadStructure("s65", null);
        profiler.start("compareStructure");
        assertEquals(65, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_filePersistenceSave2Nodes_thenPersisted() {
    	profiler.start("filePersistence2Nodes");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        profiler.start("saveStructure");
        filePersistence.saveStructure("s2", new PermutationSerializer(), tree);   
        profiler.start("loadStructure");
        var newTree = filePersistence.loadStructure("s2", new PermutationSerializer());  
        profiler.start("compareStructure");
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_H2PersistenceSave2Nodes_thenPersisted() {
    	profiler.start("H2Persistence2Nodes");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        profiler.start("saveStructure");
        H2Persistence.saveStructure("s2", new PermutationSerializer(), tree);   
        profiler.start("loadStructure");
        var newTree = H2Persistence.loadStructure("s2", new PermutationSerializer());  
        profiler.start("compareStructure");
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    } 
    
    @AfterClass
    public static void afterClass() {
        profiler.stop();
        logger.info(profiler.toString());
    }
}
