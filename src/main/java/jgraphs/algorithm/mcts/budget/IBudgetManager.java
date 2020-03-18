package jgraphs.algorithm.mcts.budget;

import java.time.Instant;

public interface IBudgetManager {
	public boolean checkStopCondition(long iterationNumber, Instant start);
	public boolean checkStopCondition(long iterationNumber);
	public long getIterations();
    public void setIterations(long iterations);    
    public long getMemory();
    public void setMemory(long memory); 
    public long getSeconds();
    public void setSeconds(long seconds);
}
