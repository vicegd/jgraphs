package jgraphs.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSerializer;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.utils.Dependency;

public class SerializerTest {
    private BacktrackingAll backtracking;

    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
    }
    
    //@Test
    public void givenStructure_serialize_thenJSONCreate() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
           
        var json = new PermutationSerializer().serialize(tree);   
        assertEquals(2, json.length());
        var newTree = new PermutationSerializer().deserialize(json); 
        assertTrue(tree.equals(newTree));
    }
    
    @Test
    public void givenStructure_serialize2_thenJSONCreate() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());   
           
        var json = new PermutationSerializer().serialize(tree);
        assertEquals(65, json.length());
        var newTree = new PermutationSerializer().deserialize(json);         
        assertTrue(tree.equals(newTree));
    }
   
}
