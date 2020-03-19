package jgraphs.microbenchmark;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class MicrobenchmarkTest {       
    @Benchmark
    public void test() {
        var a = 10;
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MicrobenchmarkTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
}

}
