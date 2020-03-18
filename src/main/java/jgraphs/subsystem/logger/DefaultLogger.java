package jgraphs.subsystem.logger;

import org.apache.log4j.PropertyConfigurator;
import jgraphs.utils.Config;

public class DefaultLogger implements ILogger {
    private org.slf4j.Logger logger;
  
    public DefaultLogger(Class<?> classType) {
    	PropertyConfigurator.configure(Config.getConfig(DefaultLogger.class).get(Config.LOGGER_PATH));
    	this.logger = org.slf4j.LoggerFactory.getLogger(classType);
    } 
    
    @Override
	public void debug(String text) {
    	this.logger.debug(text);
    }
    
    @Override
	public void error(String text) {
    	this.logger.error(text);
    }
    
    @Override
	public void info(String text) {
    	this.logger.info(text);
    }
    
    @Override
	public void trace(String text) {
    	this.logger.trace(text);
    }
    
    @Override
	public void warn(String text) {
    	this.logger.warn(text);
    }

}
