package jgraphs.profiling;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import jgraphs.logging.Logging;

public class ProfilingTest {
	protected static final Logger log = Logging.getInstance().getLogger(ProfilingTest.class);
	protected static final Profiling profiling = Profiling.getInstance();
    
    @Before
    public void initialize() {
    }
           
    //@Test
    public void givenStructure_memoryPersistenceSave65Nodes_thenPersisted() {
		profiling.createAndActivate();
	    profiling.start("test1");	    
	    var r = loop1();
	    System.out.println(r);
    	profiling.start("test2");
	    r = loop1();
	    System.out.println(r);
        profiling.stop();
        log.info(profiling.toString());
    }
    
    @Test
    public void givenStructure2_memoryPersistenceSave65Nodes_thenPersisted() {
		profiling.createAndActivate();
	    profiling.start("test1");	    
	    var r = loop1();
	    System.out.println(r);
    	profiling.start("test2");
	    r = loop1();
	    System.out.println(r);
	    
	    profiling.createNested("Nested");
	    profiling.start("Nested", "nestedTest1");
	    r = loop1();
	    System.out.println(r);
	    //profiling.stop("Nested");
	    
	    //profiling.createNested("Nested");
	    profiling.start("Nested", "nestedTest1");
	    r = loop1();
	    System.out.println(r);
	    //profiling.stop("Nested");
	    
	    profiling.start("test3");	    
	    r = loop1();
	    System.out.println(r);
	    
        profiling.stop();
        log.info(profiling.toString());
    }
    
    public long loop1() {
    	var result = 0;
    	for (var i = 0; i < 100000; i++) {
        	for (var j = 0; j < 100000; j++) {
        		result += i + j;
        	}	
    	}
    	return result;
    }
    
}
