package jgraphs.utils;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import jgraphs.core.node.INode;
import jgraphs.core.state.IState;
import jgraphs.core.structure.IGraph;
import jgraphs.core.structure.ITree;

public class Dependency {
    private static Dependency instance = null; 
    private Injector injector;
  
    private Dependency() { 
    	this.injector = Guice.createInjector(new BasicModule());	
    } 
    
    private Dependency(AbstractModule module) { 
      	this.injector = Guice.createInjector(module);	
    } 
     
    public static Dependency getInstance() { 
    	if (instance == null)
    		instance = new Dependency();
    	return instance; 
    } 
    
    public static Dependency getInstance(AbstractModule module) { 
    	if (instance == null)
    		instance = new Dependency(module);
    	return instance; 
    } 
    
    public Injector getInjector() {
    	return this.injector;
    }
    
    public INode createNodeInstance() {
    	return this.getInjector().getInstance(INode.class);
    }
    
    public IState createStateInstance() {
    	return this.getInjector().getInstance(IState.class);
    }
    
    public ITree createTreeInstance() {
    	return this.getInjector().getInstance(ITree.class);
    }
    
    public IGraph createGraphInstance() {
    	return this.getInjector().getInstance(IGraph.class);
    }
}