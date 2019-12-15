package jgraphs.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

public class PersistenceTest {
	protected static final Logger log = Logging.getInstance().getLogger(PersistenceTest.class);
	protected static final Profiling profiling = Profiling.getInstance();
    private BacktrackingAll backtracking;
    private MemoryPersistence memoryPersistence = new MemoryPersistence();
    private FilePersistence filePersistence = new FilePersistence();
    private H2Persistence H2Persistence = new H2Persistence();
    
    @BeforeClass
    public static void beforeClass() {
    	profiling.createAndActivate(PersistenceTest.class);
    }
    
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
    }
        
    @Test
    public void givenStructure_memoryPersistenceSave2Nodes_thenPersisted() {
    	profiling.start(PersistenceTest.class, "memoryPersistence2Nodes");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        profiling.start(PersistenceTest.class, "saveStructure");
        memoryPersistence.saveStructure("s2", null, tree);   
        profiling.start(PersistenceTest.class, "loadStructure");
        var newTree = memoryPersistence.loadStructure("s2", null);  
        profiling.start(PersistenceTest.class, "compareStructure");
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_memoryPersistenceSave65Nodes_thenPersisted() {
    	profiling.start(PersistenceTest.class, "memoryPersistence65Nodes");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());   
    
        profiling.start(PersistenceTest.class, "saveStructure");
        memoryPersistence.saveStructure("s65", null, tree); 
        profiling.start(PersistenceTest.class, "loadStructure");
        var newTree = memoryPersistence.loadStructure("s65", null);
        profiling.start(PersistenceTest.class, "compareStructure");
        assertEquals(65, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_filePersistenceSave2Nodes_thenPersisted() {
    	profiling.start(PersistenceTest.class, "filePersistence2Nodes");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        profiling.start(PersistenceTest.class, "saveStructure");
        filePersistence.saveStructure("s2", new PermutationSerializer(), tree);   
        profiling.start(PersistenceTest.class, "loadStructure");
        var newTree = filePersistence.loadStructure("s2", new PermutationSerializer());  
        profiling.start(PersistenceTest.class, "compareStructure");
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_H2PersistenceSave2Nodes_thenPersisted() {
    	profiling.start(PersistenceTest.class, "H2Persistence2Nodes");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        profiling.start(PersistenceTest.class, "saveStructure");
        H2Persistence.saveStructure("s2", new PermutationSerializer(), tree);   
        profiling.start(PersistenceTest.class, "loadStructure");
        var newTree = H2Persistence.loadStructure("s2", new PermutationSerializer());  
        profiling.start(PersistenceTest.class, "compareStructure");
        assertEquals(2, newTree.getNodeList().size());
        assertTrue(tree.equals(newTree));
    } 
    
    @AfterClass
    public static void afterClass() {
        profiling.stop();
        log.info(profiling.toString());
    }
}
