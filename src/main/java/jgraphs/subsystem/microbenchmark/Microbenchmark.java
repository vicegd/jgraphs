package jgraphs.subsystem.microbenchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.io.IOException;

public class Microbenchmark {
	public static class ThreadsAndQueueSizes {
	    @Param(value = {"1", "4", "8", "16", "32"})
	    String nrThreads;
	    @Param(value = { "1", "10", "100", "1000000"})
	    String queueSize;
	}
	@Benchmark
	@Fork(1)
	public void playParallel(ThreadsAndQueueSizes t3qs) throws InterruptedException {
	    int nrThreads = Integer.valueOf(t3qs.nrThreads);
	    int queueSize = Integer.valueOf(t3qs.queueSize);
	}
	@Benchmark
	@Fork(1)
	public void playSimple(){
	}
    
    public static void main(String... args) throws IOException, RunnerException {
    	org.openjdk.jmh.Main.main(args);
    }
}
