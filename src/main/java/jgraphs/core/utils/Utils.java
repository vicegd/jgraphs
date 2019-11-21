package jgraphs.core.utils;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Utils {
    private static Utils instance = null; 
    private Injector injector;
  
    private Utils() { 
      	this.injector = Guice.createInjector(new BasicModule());	
    } 
  
    public static Utils getInstance() { 
        if (instance == null) 
            instance = new Utils(); 
        return instance; 
    } 
    
    public Injector getInjector() {
    	return this.injector;
    }
}
