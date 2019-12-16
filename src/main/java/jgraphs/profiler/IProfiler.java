package jgraphs.profiler;

public interface IProfiler {
	public void create();
	public void start(String childName);
	public void stop();
}