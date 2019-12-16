package jgraphs.profiling;

import org.junit.Before;
import org.junit.Test;

import jgraphs.logger.ILogger;
import jgraphs.logger.DefaultLogger;
import jgraphs.profiler.AbstractProfiler;
import jgraphs.profiler.DefaultProfiler;
import jgraphs.profiler.IProfiler;
import jgraphs.serialization.SerializerTest;
import jgraphs.utils.BasicObject;

public class ProfilerTest extends BasicObject {
	private static final ILogger logger = new DefaultLogger(ProfilerTest.class);
	private static final IProfiler profiler = new DefaultProfiler(ProfilerTest.class);
    
	public ProfilerTest() {
		super(ProfilerTest.class);
	}
	
    @Before
    public void initialize() {
    }
           
    //@Test
    public void givenStructure_memoryPersistenceSave65Nodes_thenPersisted() {
		profiler.createAndActivate();
	    profiler.start("test1");	    
	    var r = loop1();
	    System.out.println(r);
    	profiler.start("test2");
	    r = loop1();
	    System.out.println(r);
        profiler.stop();
        logger.info(profiler.toString());
    }
    
    @Test
    public void givenStructure2_memoryPersistenceSave65Nodes_thenPersisted() {
		profiler.createAndActivate();
	    profiler.start("test1");	    
	    var r = loop1();
	    System.out.println(r);
    	profiler.start("test2");
	    r = loop1();
	    System.out.println(r);
	    
	    profiler.createNested("Nested");
	    profiler.start("Nested", "nestedTest1");
	    r = loop1();
	    System.out.println(r);
	    //profiling.stop("Nested");
	    
	    //profiling.createNested("Nested");
	    profiler.start("Nested", "nestedTest1");
	    r = loop1();
	    System.out.println(r);
	    //profiling.stop("Nested");
	    
	    profiler.start("test3");	    
	    r = loop1();
	    System.out.println(r);
	    
        profiler.stop();
        logger.info(profiler.toString());
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
