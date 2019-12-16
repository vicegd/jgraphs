package jgraphs.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import jgraphs.logger.DefaultLogger;
import jgraphs.logger.ILogger;

public class Config {
	protected static final ILogger logger = new DefaultLogger(Config.class);
  
    public static HashMap<String, String> getConfig(Class<?> classType) {
    	var path = classType.getSimpleName();
    	var config = new HashMap<String, String>();
    	try (InputStream input = new FileInputStream("configs/" + path + ".properties")) {
            var prop = new Properties();
            prop.load(input);
            for (var p : prop.stringPropertyNames()) {
            	config.put(p, prop.getProperty(p));
            }
        } catch (IOException ex) {
       		logger.error(ex.getMessage());
        }
    	return config;
    }

    public static final String ABSTRACT_GRAPH_VISUALIZER_PATH = "path";
    public static final String ABSTRACT_PROFILER_DEFAULT_NAME = "default.name";
    public static final String ABSTRACT_PROFILER_FORCE_DISABLED_ALL = "force.disabled.all";
    public static final String ABSTRACT_PROFILER_FORCE_DISABLED_MEASUREMENTS = "force.disabled.measurements";
    public static final String ABSTRACT_PROFILER_PATH = "path";
    public static final String ABSTRACT_PROFILER_PERFORMANCE_TEXT_FILE = "performance.text.file";
    public static final String DEFAULT_BUDGET_MANAGER_ITERATIONS = "iterations";
    public static final String DEFAULT_BUDGET_MANAGER_MEMORY = "memory";
    public static final String DEFAULT_BUDGET_MANAGER_SECONDS = "seconds";    
    public static final String DEFAULT_COMPARATOR_PATH = "path";
    public static final String FILE_PERSISTENCE_PATH = "path";
    public static final String H2_PERSISTENCE_DB_URL = "db.url";
    public static final String H2_PERSISTENCE_JDBC_DRIVER = "jdbc.driver";
    public static final String H2_PERSISTENCE_PASS = "pass";
    public static final String H2_PERSISTENCE_USER = "user";
    public static final String LOGGER_PATH = "path";
    public static final String MCTS_TRAINERS = "trainers";
    public static final String UCB_C = "c";
    public static final String UCB_DRAW_SCORE = "draw.score";
    public static final String UCB_LOSE_SCORE = "lose.score";
    public static final String UCB_WIN_SCORE = "win.score";
}
