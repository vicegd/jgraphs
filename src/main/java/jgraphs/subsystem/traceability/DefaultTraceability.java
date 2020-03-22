package jgraphs.subsystem.traceability;

import java.io.IOException;

import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;

public class DefaultTraceability implements ITraceability {
	protected static final ILogger logger = new DefaultLogger(DefaultTraceability.class);
	protected int stops = 0;
	
	@Override
	public void pause(IStructure structure) {
		this.stops++;
		try {
			logger.info("Press enter to continue (structure with " + structure.getNodeList().size() + " nodes)");
			System.in.read();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	public int getStops() {
		return this.stops;
	}
}
