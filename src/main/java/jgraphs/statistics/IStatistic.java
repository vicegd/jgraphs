package jgraphs.statistics;

import java.time.Duration;

import jgraphs.core.tree.ITree;

public interface IStatistic {
	public void processFinishedEvent(ITree tree, Duration processDuration, Duration totalDuration);
}
