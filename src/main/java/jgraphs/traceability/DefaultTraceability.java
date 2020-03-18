package jgraphs.traceability;

import java.io.IOException;

import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;

public class DefaultTraceability implements ITraceability {
	protected static final ILogger logger = new DefaultLogger(DefaultTraceability.class);
	
	@Override
	public void pause(IStructure structure) {
		try {
			logger.info("Press enter to continue" + structure.getNodeList().size());
			System.in.read();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
