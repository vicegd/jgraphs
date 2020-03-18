package jgraphs.algorithm.mcts.budget;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

import jgraphs.utils.Config;

public class DefaultBudgetManager implements IBudgetManager {
	protected static final HashMap<String, String> config = Config.getConfig(DefaultBudgetManager.class);
	protected long iterations;
	protected long memory;
	protected long seconds;
	
	public DefaultBudgetManager() {
        this.iterations = Long.parseLong(config.get(Config.DEFAULT_BUDGET_MANAGER_ITERATIONS));
        this.memory = Long.parseLong(config.get(Config.DEFAULT_BUDGET_MANAGER_MEMORY));
        this.seconds = Long.parseLong(config.get(Config.DEFAULT_BUDGET_MANAGER_SECONDS));
	}
	
	@Override
    public boolean checkStopCondition(long iterationNumber) {
        var memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (memoryUsed >= this.memory) return true; //break because of memory
                      
        if (iterationNumber >= this.iterations) return true; //break because of iterations
        return false;
    }
	
	@Override
    public boolean checkStopCondition(long iterationNumber, Instant start) {
        if (this.checkStopCondition(iterationNumber)) return true;
              
       	var duration = Duration.between(start, Instant.now());       
       	if (duration.getSeconds() >= this.seconds) return true; //break because of seconds
        
        return false;
    }
    
	@Override
	public long getIterations() {
		return this.iterations;
	}
    
    public void setIterations(long iterations) {
    	this.iterations = iterations;
    }
    
	@Override
	public long getMemory() {
		return this.memory;
	}
    
	@Override
    public void setMemory(long memory) {
    	this.memory = memory;
    }
    
	@Override
	public long getSeconds() {
		return this.seconds;
	} 
	
	@Override
    public void setSeconds(long seconds) {
    	this.seconds = seconds;
    }
}