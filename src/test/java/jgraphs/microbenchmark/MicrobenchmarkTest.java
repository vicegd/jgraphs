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
    @Test
    public void test() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MicrobenchmarkTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

}
