package jgraphs.profiling;

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
		String tsDbFile = config.get("profiling_path") + config.get("db_file");
		String tsTextFile = config.get("profiling_path") + config.get("text_file");
		log.info("\nTime Series DB (TSDB) : {}\nTime Series text file : {}", tsDbFile, tsTextFile);
		System.setProperty("spf4j.perf.ms.config", "TSDB@" + tsDbFile + "," + "TSDB_TXT@" + tsTextFile);
	}
	
	public static MeasurementRecorder getMeasurementRecorder(Object forWhat) {
	    String unitOfMeasurement = "ms";
	    int sampleTimeMillis = 1_000;
	    int factor = 10;
	    int lowerMagnitude = 0;
	    int higherMagnitude = 4;
	    int quantasPerMagnitude = 10;
	 
	    return RecorderFactory.createScalableQuantizedRecorder(
	      forWhat, unitOfMeasurement, sampleTimeMillis, factor, lowerMagnitude, 
	      higherMagnitude, quantasPerMagnitude);
	}
	
	/*public static final class RecorderSourceForIsPrimeNumber extends RecorderSourceInstance {
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
	}*/
}
