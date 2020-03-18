package jgraphs.subsystem.profiler;

import org.spf4j.perf.MeasurementRecorder;
import org.spf4j.perf.impl.RecorderFactory;

public class DefaultProfiler extends AbstractProfiler {
	public DefaultProfiler(Class<?> classType) { 
    	super(classType);
    	if ((!this.forceDisabledAll)&&(!this.forceDisabledMeasurements))
    		super.measurementRecorder = this.getMeasurementRecorder(className);
    } 
	
	@Override
	protected MeasurementRecorder getMeasurementRecorder(Object forWhat) {
	    var unitOfMeasurement = "ms";
	    var sampleTimeMillis = 1000;
	    var factor = 10;
	    var lowerMagnitude = 0;
	    var higherMagnitude = 4;
	    var quantasPerMagnitude = 10;
	
	    return RecorderFactory.createScalableQuantizedRecorder(
	      forWhat, unitOfMeasurement, sampleTimeMillis, factor, lowerMagnitude, 
	      higherMagnitude, quantasPerMagnitude);
	}
}