package jgraphs.subsystem.logger;

public interface ILogger {
	void debug(String text);
	void error(String text);
	void info(String text);
	void trace(String text);
	void warn(String text);
}