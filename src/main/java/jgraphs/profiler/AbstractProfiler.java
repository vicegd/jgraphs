package jgraphs.profiler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.profiler.TimeInstrument;
import org.spf4j.perf.MeasurementRecorder;

import jgraphs.logger.ILogger;
import jgraphs.logger.DefaultLogger;
import jgraphs.utils.Config;

public abstract class AbstractProfiler implements IProfiler {
	protected static final HashMap<String, String> config = Config.getConfig(AbstractProfiler.class);
	protected static final ILogger logger = new DefaultLogger(AbstractProfiler.class);
	protected static final String DEFAULT_NAME = config.get(Config.ABSTRACT_PROFILER_DEFAULT_NAME);
	protected boolean activate;
	protected boolean forceDisabledAll;
	protected boolean forceDisabledMeasurements;
	protected HashMap<String, org.slf4j.profiler.Profiler> profilers;
	protected TimeInstrument timeInstrument;
	protected MeasurementRecorder measurementRecorder;
	protected long startTime;
	protected String className;

	public AbstractProfiler(Class<?> classType) { 
    	this.activate = false;
    	this.forceDisabledAll = Boolean.parseBoolean(config.get(Config.ABSTRACT_PROFILER_FORCE_DISABLED_ALL));
    	this.forceDisabledMeasurements = Boolean.parseBoolean(config.get(Config.ABSTRACT_PROFILER_FORCE_DISABLED_MEASUREMENTS));
    	this.profilers = new HashMap<String, org.slf4j.profiler.Profiler>();
    	this.className = classType.getSimpleName();
    	if ((!this.forceDisabledAll)&&(!this.forceDisabledMeasurements))
        	this.initializeRecorder();    	
    } 
     
	protected void initializeRecorder() {
		var dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");		
		var dbFile = config.get(Config.ABSTRACT_PROFILER_PATH) + dateFormat.format(new Date()) + ".tsdb2";
		var textFile = config.get(Config.ABSTRACT_PROFILER_PATH) + config.get(Config.ABSTRACT_PROFILER_PERFORMANCE_TEXT_FILE);
		logger.debug(String.format("\nTime Series DB (TSDB) : {}\nTime Series text file : {}", dbFile, textFile));
		System.setProperty("spf4j.perf.ms.config", "TSDB@" + dbFile + "," + "TSDB_TXT@" + textFile);
	}
	
	protected abstract MeasurementRecorder getMeasurementRecorder(Object forWhat);
       
    @Override
	public void create() {
    	this.activate = true;
    	this.create(DEFAULT_NAME);
    	this.createNested(DEFAULT_NAME, this.className);
    	if ((!this.forceDisabledAll)&&(!this.forceDisabledMeasurements))
    		this.startTime = System.currentTimeMillis();
    }
       
    @Override
	public void start(String childName) {
    	if ((!this.forceDisabledAll)&&(this.activate)) {
    		if (!this.forceDisabledMeasurements) 
    			this.measurementRecorder.record(System.currentTimeMillis() - this.startTime);
    		this.profilers.get(this.className).start(childName);
    	}
    }
    
    @Override
	public void stop() {
    	if ((!this.forceDisabledAll)&&(this.activate)) {
    		this.timeInstrument = this.profilers.get(DEFAULT_NAME).stop();
    	}
    }
    
    @Override
	public String toString() {
    	if ((!this.forceDisabledAll)&&(this.activate)) {
    		return this.timeInstrument.toString();
    	}
    	return "Nothing to show. Profiler is currently disabled.";
    }
    
    protected void create(String profilerName) {
    	if ((!this.forceDisabledAll)&&(this.activate)) {
	    	var profiler = new org.slf4j.profiler.Profiler(profilerName);
	    	this.profilers.put(profilerName, profiler);
    	}
    }
        
    protected void createNested(String rootProfilerName, String nestedProfilerName) {
    	if ((!this.forceDisabledAll)&&(this.activate)) {
		    var profiler = profilers.get(rootProfilerName).startNested(nestedProfilerName);
		    this.profilers.put(nestedProfilerName, profiler);
    	}
    }
}