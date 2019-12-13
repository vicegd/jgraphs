package jgraphs.traceability;

import java.io.IOException;

import org.slf4j.Logger;

import jgraphs.core.structure.IStructure;
import jgraphs.logging.Logging;

public class BasicTraceability implements ITraceability {
	private static Logger log = Logging.getInstance().getLogger(BasicTraceability.class);
	
	@Override
	public void pause(IStructure structure) {
		try {
			log.info("Press enter to continue");
			System.in.read();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
