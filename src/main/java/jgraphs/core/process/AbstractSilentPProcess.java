package jgraphs.core.process;

import java.util.Collections;

import jgraphs.logger.DefaultLogger;
import jgraphs.logger.ILogger;

public abstract class AbstractSilentPProcess extends AbstractSilentProcess {
	protected static final ILogger logger = new DefaultLogger(AbstractSilentPProcess.class);
	
	public AbstractSilentPProcess() {
		super();
    	super.results = Collections.synchronizedList(super.results);
	}
}