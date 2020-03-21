package jgraphs.subsystem.statistics;

import java.time.Instant;

import jgraphs.core.structure.IStructure;

public interface IStatistic {
	public void checkpointEvent(IStructure structure, Instant start, Instant end);
}
