package jgraphs.profiling;

import org.junit.Before;
import org.junit.Test;
import org.spf4j.perf.MeasurementRecorder;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.utils.Dependency;

public class ProfilingTest {
    private BacktrackingAll backtracking;
    
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule()).getInjector().getInstance(BacktrackingAll.class);
    }
           
    @Test
    public void givenStructure_memoryPersistenceSave65Nodes_thenPersisted() {
		Profiling.initialize();
		MeasurementRecorder measurementRecorder = Profiling.getMeasurementRecorder(ProfilingTest.class + "execute");
		long startTime = System.currentTimeMillis();
    	
    	var tree = this.backtracking.getStructure();
    	tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.execute(tree.getFirst());   
            
        measurementRecorder.record(System.currentTimeMillis() - startTime);
    }
    
}
