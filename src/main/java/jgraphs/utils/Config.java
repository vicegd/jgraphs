package jgraphs.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;

import jgraphs.logging.Logging;

public class Config {
	protected static final Logger log = Logging.getInstance().getLogger(Config.class);
	private static Config instance = null; 
  
    private Config() { 
    } 
     
    public static Config getInstance() { 
    	if (instance == null)
    		instance = new Config();
    	return instance; 
    } 
    
    public HashMap<String, String> getConfig(Class<?> className) {
    	var path = className.getSimpleName();
    	var config = new HashMap<String, String>();
    	try (InputStream input = new FileInputStream("configs/" + path + ".properties")) {
            var prop = new Properties();
            prop.load(input);
            for (var p : prop.stringPropertyNames()) {
            	config.put(p, prop.getProperty(p));
            }
        } catch (IOException ex) {
       		log.error(ex.getMessage());
        }
    	return config;
    }

    public static final String DEFAULT_COMPARATOR_PATH = "path";
    public static final String FILE_PERSISTENCE_PATH = "path";
    public static final String H2_PERSISTENCE_DB_URL = "db.url";
    public static final String H2_PERSISTENCE_JDBC_DRIVER = "jdbc.driver";
    public static final String H2_PERSISTENCE_PASS = "pass";
    public static final String H2_PERSISTENCE_USER = "user";
    public static final String LOGGING_PATH = "path";
    public static final String PROFILING_DEFAULT_NAME = "default.name";
    public static final String PROFILING_FORCE_DISABLED = "force.disabled";
    public static final String PROFILING_DB_FILE = "db.file";
    public static final String PROFILING_PATH = "path";
    public static final String PROFILING_TEXT_FILE = "text.file";
}
