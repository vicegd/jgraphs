package jgraphs.serialization;

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

public class SerializerTest {
	protected static final Logger log = Logging.getInstance().getLogger(SerializerTest.class);
	protected static final Profiling profiling = Profiling.getInstance();
    private BacktrackingAll backtracking;

    @BeforeClass
    public static void beforeClass() {
    	profiling.createAndActivate(SerializerTest.class);
    }
    
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
    }
    
    @Test
    public void givenStructure1Node_serialize_thenJSONCreate() {
    	profiling.start(SerializerTest.class, "structure1Node");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
           
        profiling.start(SerializerTest.class, "serialize");
        var json = new PermutationSerializer().serialize(tree);   
        assertEquals(2, json.length());
        
        profiling.start(SerializerTest.class, "deserialize");
        var newTree = new PermutationSerializer().deserialize(json); 
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure65Nodes_serialize_thenJSONCreate() {
    	profiling.start(SerializerTest.class, "structure65Nodes");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());   
           
        profiling.start(SerializerTest.class, "serialize");
        var json = new PermutationSerializer().serialize(tree);
        assertEquals(65, json.length());
        
        profiling.start(SerializerTest.class, "deserialize");
        var newTree = new PermutationSerializer().deserialize(json);         
        assertTrue(tree.equals(newTree));
    }
    
    @AfterClass
    public static void afterClass() {
        profiling.stop();
        log.info(profiling.toString());
    }
   
}
