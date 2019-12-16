package jgraphs.algorithm.mcts.budget;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

import jgraphs.logger.ILogger;
import jgraphs.logger.DefaultLogger;

public class DefaultBudgetManager implements IBudgetManager {
	protected static final ILogger logger = new DefaultLogger(DefaultBudgetManager.class);
	private long iterations;
	private long memory;
	private long seconds;
	
	public DefaultBudgetManager() {
    	try (InputStream input = new FileInputStream("src/main/java/config.properties")) {
            var prop = new Properties();
            prop.load(input);
            this.iterations = Long.parseLong(prop.getProperty("budget.iterations"));
            this.memory = Long.parseLong(prop.getProperty("budget.memory"));
            this.seconds = Long.parseLong(prop.getProperty("budget.seconds"));
       } catch (IOException ex) {
       		logger.error(ex.getMessage());
       }
	}
	
    public boolean checkStopCondition(long iterationNumber, Instant start) {
        var memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (memoryUsed >= this.memory) return true; //break because of memory
               
        var duration = Duration.between(start, Instant.now());       
        if (duration.getSeconds() >= this.seconds) return true; //break because of seconds
        
        if (iterationNumber >= this.iterations) return true; //break because of iterations
        return false;
    }
    
    public void setIterations(long iterations) {
    	this.iterations = iterations;
    }
    
    public void setMemory(long memory) {
    	this.memory = memory;
    }
    
    public void setSeconds(long seconds) {
    	this.seconds = seconds;
    }
      
}
