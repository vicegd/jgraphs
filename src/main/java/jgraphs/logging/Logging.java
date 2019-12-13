package jgraphs.logging;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.utils.Config;

public class Logging {
	private static Logging instance = null; 
  
    private Logging() { 
        PropertyConfigurator.configure(Config.getInstance().getConfig(Logging.class).get("config_file_path"));
    } 
     
    public static Logging getInstance() { 
    	if (instance == null)
    		instance = new Logging();
    	return instance; 
    } 
    
    public Logger getLogger(Class<?> className) {
        return LoggerFactory.getLogger(className);
    }
   
}
