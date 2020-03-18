package jgraphs.core.utils;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import jgraphs.core.node.INode;
import jgraphs.core.structure.IGraph;
import jgraphs.core.structure.ITree;

public class Utils {
    private static Utils instance = null; 
    private Injector injector;
  
    private Utils() { 
    	this.injector = Guice.createInjector(new BasicModule());	
    } 
    
    private Utils(AbstractModule module) { 
      	this.injector = Guice.createInjector(module);	
    } 
     
    public static Utils getInstance() { 
    	if (instance == null)
    		instance = new Utils();
    	return instance; 
    } 
    
    public static Utils getInstance(AbstractModule module) { 
    	if (instance == null)
    		instance = new Utils(module);
    	return instance; 
    } 
    
    public Injector getInjector() {
    	return this.injector;
    }
    
    public INode createNodeInstance() {
    	return this.getInjector().getInstance(INode.class);
    }
    
    public ITree createTreeInstance() {
    	return this.getInjector().getInstance(ITree.class);
    }
    
    public IGraph createGraphInstance() {
    	return this.getInjector().getInstance(IGraph.class);
    }
}
