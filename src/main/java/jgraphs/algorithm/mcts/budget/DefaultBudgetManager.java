package jgraphs.algorithm.mcts.budget;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

import jgraphs.utils.Config;

public class DefaultBudgetManager implements IBudgetManager {
	private static final HashMap<String, String> config = Config.getConfig(DefaultBudgetManager.class);
	private long iterations;
	private long memory;
	private long seconds;
	
	public DefaultBudgetManager() {
        this.iterations = Long.parseLong(config.get(Config.DEFAULT_BUDGET_MANAGER_ITERATIONS));
        this.memory = Long.parseLong(config.get(Config.DEFAULT_BUDGET_MANAGER_MEMORY));
        this.seconds = Long.parseLong(config.get(Config.DEFAULT_BUDGET_MANAGER_SECONDS));
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