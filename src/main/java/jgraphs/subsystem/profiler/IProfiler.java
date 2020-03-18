package jgraphs.subsystem.profiler;

public interface IProfiler {
	public void create();
	public void start(String name);
	public void stop();
}