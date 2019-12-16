package jgraphs.profiler;

public interface IProfiler {
	public void create();
	public void start(String name);
	public void stop();
}