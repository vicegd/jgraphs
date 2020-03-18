package jgraphs.app.permutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.core.utils.Utils;

public class PermutationSerializerTest {
	protected static Logger log = LoggerFactory.getLogger(PermutationSerializerTest.class);
    private BacktrackingAll backtracking;

    @Before
    public void initialize() {
        this.backtracking = Utils.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
    }
    
    @Test
    public void givenStructure_serialize_thenJSONCreate() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        var json = new PermutationSerializer().serialize(tree);   
        assertEquals(2, json.length());
        
        var structure = new PermutationSerializer().deserialize(json);     
        assertEquals(2, structure.getNodeList().size());
    }
    
    //@Test
    public void givenStructure_serialize_thenFileCreate() {
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.execute(tree.getFirst());   
    
        var file = "serializer/test.json";
        
        var json = new PermutationSerializer().serialize(tree, file);   
        assertEquals(2, json.length());
        
        var structure = new PermutationSerializer().deserialize(file);     
        assertEquals(2, structure.getNodeList().size());
    }
   
}
