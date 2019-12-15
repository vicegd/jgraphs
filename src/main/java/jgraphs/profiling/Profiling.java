package jgraphs.profiling;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.profiler.Profiler;
import org.slf4j.profiler.TimeInstrument;

import jgraphs.logging.Logging;
import jgraphs.utils.Config;

public class Profiling {
	protected static final HashMap<String, String> config = Config.getInstance().getConfig(Profiling.class);
	protected static final Logger log = Logging.getInstance().getLogger(Profiling.class);
	private static final String DEFAULT_NAME = config.get(Config.PROFILING_DEFAULT_NAME);
	private static Profiling instance = null; 
	private boolean activate = false;
	private boolean forceDisabled = Boolean.parseBoolean(config.get(Config.PROFILING_FORCE_DISABLED));
	private HashMap<String, Profiler> profilers = new HashMap<String, Profiler>();
	private HashMap<String, TimeInstrument> timeInstruments = new HashMap<String, TimeInstrument>();
  
    private Profiling() { 
    } 
     
    public static Profiling getInstance() { 
    	if (instance == null)
    		instance = new Profiling();
    	return instance; 
    } 
    
    public void setActivate(boolean activate) {
    	this.activate = activate;
    }
    
    public void create() {
    	this.create(DEFAULT_NAME);
    }
    
    public void createAndActivate() {
    	this.setActivate(true);
    	this.create(DEFAULT_NAME);
    }
    
    public void createAndActivate(Class<?> nestedProfilerClass) {
    	this.setActivate(true);
    	this.create(DEFAULT_NAME);
    	this.createNested(nestedProfilerClass);
    }
    
    public void create(String profilerName) {
    	if ((!this.forceDisabled)&&(this.activate)) {
	    	var profiler = new Profiler(profilerName);
	    	profilers.put(profilerName, profiler);
    	}
    }
    
    public void createNested(String nestedProfilerName) {
    	this.createNested(DEFAULT_NAME, nestedProfilerName);
    }
    
    public void createNested(Class<?> nestedProfilerClass) {
    	this.createNested(DEFAULT_NAME, nestedProfilerClass.getSimpleName());
    }
    
    public void createNested(String rootProfilerName, String nestedProfilerName) {
    	if ((!this.forceDisabled)&&(this.activate)) {
		    var profiler = profilers.get(rootProfilerName).startNested(nestedProfilerName);
		    profilers.put(nestedProfilerName, profiler);
    	}
    }
    
    public void startMethod(Class<?> profilerClass, String methodName) {
    	var childName = profilerClass.getSimpleName() + ": " + methodName;
    	this.start(childName);
    }
    
    public void startNested(Class<?> profilerClass, String childName) {
    	this.createNested(profilerClass);
    	this.start(profilerClass, childName);
    }
    
    public void start(String childName) {
    	this.start(DEFAULT_NAME, childName);
    }
    
    public void start(Class<?> profilerClass, String childName) {
    	this.start(profilerClass.getSimpleName(), childName);
    }
    
    public void start(String profilerName, String childName) {
    	if ((!this.forceDisabled)&&(this.activate)) {
	    	profilers.get(profilerName).start(childName);
    	}
    }
    
    public void stop() {
    	this.stop(DEFAULT_NAME);
    }
    
    public void stop(Class<?> profilerClass) {
    	this.stop(profilerClass.getSimpleName());
    }
    
    public void stop(String profilerName) {
    	if ((!this.forceDisabled)&&(this.activate)) {
    		var timeInstrument = profilers.get(profilerName).stop();
    		//System.out.println("A2:" + timeInstrument.toString());
	    	timeInstruments.put(profilerName, timeInstrument);
    	}
    }
    
    public String toString() {
   		return this.toString(DEFAULT_NAME);
    }
    
    public String toString(String profilerName) {
    	if ((!this.forceDisabled)&&(this.activate)) {
    		return timeInstruments.get(profilerName).toString();
    	}
    	return "Nothing to show. Profiler is currently disabled.";
    }
}

/*
import java.util.HashMap;

import org.slf4j.Logger;
import org.spf4j.perf.MeasurementRecorder;
import org.spf4j.perf.impl.RecorderFactory;

import jgraphs.logging.Logging;
import jgraphs.utils.Config;

public class Profiling {
	protected static final HashMap<String, String> config = Config.getInstance().getConfig(Profiling.class);
	protected static final Logger log = Logging.getInstance().getLogger(Profiling.class);
	
	public static void initialize() {
		var dbFile = config.get(Config.PROFILING_PATH) + config.get(Config.PROFILING_DB_FILE);
		var textFile = config.get(Config.PROFILING_PATH) + config.get(Config.PROFILING_TEXT_FILE);
		log.info("\nTime Series DB (TSDB) : {}\nTime Series text file : {}", dbFile, textFile);
		System.setProperty("spf4j.perf.ms.config", "TSDB@" + dbFile + "," + "TSDB_TXT@" + textFile);
	}
	
	public static MeasurementRecorder getMeasurementRecorder(Object forWhat) {
	    var unitOfMeasurement = "ms";
	    var sampleTimeMillis = 1_000;
	    var factor = 10;
	    var lowerMagnitude = 0;
	    var higherMagnitude = 4;
	    var quantasPerMagnitude = 10;
	 
	    return RecorderFactory.createScalableQuantizedRecorder(
	      forWhat, unitOfMeasurement, sampleTimeMillis, factor, lowerMagnitude, 
	      higherMagnitude, quantasPerMagnitude);
	}
	
	public static final class RecorderSourceForIsPrimeNumber extends RecorderSourceInstance {
	    public static final MeasurementRecorderSource INSTANCE;
	    static {
	        Object forWhat = AbstractProcess.class + " execute";
	        String unitOfMeasurement = "ms";
	        int sampleTimeMillis = 1_000;
	        int factor = 10;
	        int lowerMagnitude = 0;
	        int higherMagnitude = 4;
	        int quantasPerMagnitude = 10;
	        INSTANCE = RecorderFactory.createScalableQuantizedRecorderSource(
	          forWhat, unitOfMeasurement, sampleTimeMillis, factor, 
	          lowerMagnitude, higherMagnitude, quantasPerMagnitude);
	    }
	}
}*/
