package jgraphs.serialization;

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
import jgraphs.subsystem.profiler.DefaultProfiler;
import jgraphs.subsystem.profiler.IProfiler;
import jgraphs.utils.Dependency;

public class SerializerTest {
	private static final ILogger logger = new DefaultLogger(SerializerTest.class);
	private static final IProfiler profiler = new DefaultProfiler(SerializerTest.class);
    private BacktrackingAll backtracking;
    
    @BeforeClass
    public static void beforeClass() {
    	profiler.create();
    }
    
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
    }
    
    @Test
    public void givenStructure1Node_serialize_thenJSONCreate() {    	
    	profiler.start("structure1Node");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   

        profiler.start("serialize");
        var json = new PermutationSerializer().serialize(tree);   
        assertEquals(2, json.length());
        
        profiler.start("deserialize");
        var newTree = new PermutationSerializer().deserialize(json); 
        assertTrue(tree.equals(newTree));
    }
        
    @Test
    public void givenStructure65Nodes_serialize_thenJSONCreate() {
    	profiler.start("structure65Nodes");
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());   
           
        profiler.start("serialize");
        var json = new PermutationSerializer().serialize(tree);
        assertEquals(65, json.length());
        
        profiler.start("deserialize");
        var newTree = new PermutationSerializer().deserialize(json);         
        assertTrue(tree.equals(newTree));
    }
    
    @AfterClass
    public static void afterClass() {
        profiler.stop();
        logger.info(profiler.toString());
    }
   
}
