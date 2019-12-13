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
   
}
