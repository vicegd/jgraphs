package jgraphs.algorithm.mcts.budget;

import java.time.Instant;

public interface IBudgetManager {
	public boolean checkStopCondition(long iterationNumber, Instant start);
    public void setIterations(long iterations);    
    public void setMemory(long memory);   
    public void setSeconds(long seconds);
}
