package jgraphs.utils;

import org.apache.log4j.PropertyConfigurator;

public class Logger {
    private static Logger instance = null; 
  
    private Logger() { 
        PropertyConfigurator.configure("configs/log4j.properties");
    } 
     
    public static Logger getInstance() { 
    	if (instance == null)
    		instance = new Logger();
    	return instance; 
    } 
    
    public org.slf4j.Logger getLogger(Class<?> className) {
        return org.slf4j.LoggerFactory.getLogger(className);
    }
   
}
