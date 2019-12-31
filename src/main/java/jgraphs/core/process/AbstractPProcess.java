package jgraphs.core.process;

import java.util.Collections;

import jgraphs.logger.DefaultLogger;
import jgraphs.logger.ILogger;

public abstract class AbstractPProcess extends AbstractProcess {
	protected static final ILogger logger = new DefaultLogger(AbstractPProcess.class);
	
	public AbstractPProcess() {
		super();
    	super.visualizers = Collections.synchronizedList(super.visualizers);
    	super.statistics = Collections.synchronizedList(super.statistics);
    	super.traceabilities = Collections.synchronizedList(super.traceabilities);
    	super.results = Collections.synchronizedList(super.results);
	}
	
}