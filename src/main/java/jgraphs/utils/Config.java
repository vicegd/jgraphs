package jgraphs.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import jgraphs.logger.ILogger;
import jgraphs.logger.DefaultLogger;

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

    public static final String DEFAULT_COMPARATOR_PATH = "path";
    public static final String FILE_PERSISTENCE_PATH = "path";
    public static final String H2_PERSISTENCE_DB_URL = "db.url";
    public static final String H2_PERSISTENCE_JDBC_DRIVER = "jdbc.driver";
    public static final String H2_PERSISTENCE_PASS = "pass";
    public static final String H2_PERSISTENCE_USER = "user";
    public static final String LOGGER_PATH = "path";
    public static final String ABSTRACT_PROFILER_DEFAULT_NAME = "default.name";
    public static final String ABSTRACT_PROFILER_FORCE_DISABLED_ALL = "force.disabled.all";
    public static final String ABSTRACT_PROFILER_FORCE_DISABLED_MEASUREMENTS = "force.disabled.measurements";
    public static final String ABSTRACT_PROFILER_PATH = "path";
    public static final String ABSTRACT_PROFILER_PERFORMANCE_TEXT_FILE = "performance.text.file";
}
