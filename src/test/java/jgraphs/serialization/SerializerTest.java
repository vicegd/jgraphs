package jgraphs.serialization;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSerializer;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class SerializerTest {
    private BacktrackingAll backtracking;
    
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule(EModuleConfiguration.BASIC)).getInjector(BacktrackingAll.class);
    }
    
    @Test
    public void givenStructure1Element_serialize_thenJSONCreate() {    	
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();   

        var json = new PermutationSerializer().serialize(tree);   
        assertEquals(2, json.length());
        
        var newTree = new PermutationSerializer().deserialize(json); 
        var jsonNewTree = new PermutationSerializer().serialize(newTree); 
                
        assertEquals(json.toString(), jsonNewTree.toString());
    }
    
    @Test
    public void givenStructure2Elements_serialize_thenJSONCreate() {    	
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(2));
        this.backtracking.run();   

        var json = new PermutationSerializer().serialize(tree);   
        assertEquals(5, json.length());
        
        var newTree = new PermutationSerializer().deserialize(json); 
        var jsonNewTree = new PermutationSerializer().serialize(newTree); 
                
        assertEquals(json.toString(), jsonNewTree.toString());
    }
        
    @Test
    public void givenStructure65Elements_serialize_thenJSONCreate() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.run();   
           
        var json = new PermutationSerializer().serialize(tree);
        assertEquals(65, json.length());
        
        var newTree = new PermutationSerializer().deserialize(json); 
        var jsonNewTree = new PermutationSerializer().serialize(newTree); 
                
        assertEquals(json.toString(), jsonNewTree.toString());
    }
}
