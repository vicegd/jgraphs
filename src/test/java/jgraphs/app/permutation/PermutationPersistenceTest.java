package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.core.persistence.FilePersistence;
import jgraphs.core.persistence.MemoryPersistence;
import jgraphs.core.persistence.H2Persistence;
import jgraphs.core.utils.Utils;

public class PermutationPersistenceTest {
	protected static Logger log = LoggerFactory.getLogger(PermutationPersistenceTest.class);
    private BacktrackingAll backtracking;
    private MemoryPersistence memoryPersistence;
    private FilePersistence filePersistence;
    private H2Persistence H2Persistence;
    
    @Before
    public void initialize() {
        this.backtracking = Utils.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
        this.memoryPersistence = new MemoryPersistence();
        this.filePersistence = new FilePersistence();
        this.H2Persistence = new H2Persistence();
    }
        
   // @Test
    public void givenStructure_memoryPersistenceSave2Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        memoryPersistence.saveStructure("s2", null, tree);  
        
        var structure = memoryPersistence.loadStructure("s2", null);  
        assertEquals(2, structure.getNodeList().size());
    }
    
   // @Test
    public void givenStructure_memoryPersistenceSave65Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());   
    
        memoryPersistence.saveStructure("s65", null, tree);  
        
        var structure = memoryPersistence.loadStructure("s65", null);    
        assertEquals(65, structure.getNodeList().size());
    }
    
    //@Test
    public void givenStructure_filePersistenceSave2Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        filePersistence.saveStructure("s2", new PermutationSerializer(), tree);  
        
        //var structure = filePersistence.loadStructure("s22", new PermutationSerializer());  
        //assertEquals(2, structure.getNodeList().size());
    }
    
    @Test
    public void givenStructure_H2PersistenceSave2Nodes_thenPersisted() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        H2Persistence.saveStructure("s2", new PermutationSerializer(), tree);  
        
        //var structure = filePersistence.loadStructure("s22", new PermutationSerializer());  
        //assertEquals(2, structure.getNodeList().size());
    }
    
    
}
