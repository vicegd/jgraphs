package jgraphs.subsystem.profiler;

import org.jfree.util.Log;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.subsystem.profiler.DefaultProfiler;
import jgraphs.subsystem.profiler.IProfiler;

public class ProfilerTest {
	private static final ILogger logger = new DefaultLogger(ProfilerTest.class);
	private static final IProfiler profiler = new DefaultProfiler(ProfilerTest.class);

    @BeforeClass
    public static void beforeClass() {
    	profiler.create();
    }
    
    @Test
    public void test1() {
    	profiler.start("test1");
    }

    @Test
    public void test2() {
    	long operation = 0;
    	for (int i=0; i<1000; i++) {
    		operation += Math.sqrt(i);
    	}
    	Log.debug(operation);
    	profiler.start("test2");
    }
   
    @Test
    public void test3() {
    	long operation = 0;
    	for (int i=0; i<1000000; i++) {
    		operation += Math.sqrt(i);
    	}
    	Log.debug(operation);
    	profiler.start("test3");
    }
  
    @AfterClass
    public static void afterClass() {
        profiler.stop();
        logger.info(profiler.toString());
    }

}