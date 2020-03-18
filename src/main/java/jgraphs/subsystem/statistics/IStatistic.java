package jgraphs.subsystem.statistics;

import java.time.Duration;

import jgraphs.core.structure.IStructure;

public interface IStatistic {
	public void processFinishedEvent(IStructure structure, Duration processDuration, Duration totalDuration);
}
