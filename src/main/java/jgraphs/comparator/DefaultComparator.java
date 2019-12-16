package jgraphs.comparator;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.JSONArray;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;

import jgraphs.core.structure.IStructure;
import jgraphs.logger.ILogger;
import jgraphs.logger.DefaultLogger;
import jgraphs.serialization.ISerializer;
import jgraphs.utils.Config;

public class DefaultComparator implements IComparator {
	protected static final HashMap<String, String> config = Config.getConfig(DefaultComparator.class);
	protected static final ILogger logger = new DefaultLogger(DefaultComparator.class);
	
	@Override
	public JSONArray compare(IStructure sourceStructure, IStructure targetStructure, ISerializer serializer) {
		JSONArray result = null;
		
		var objectMapper = new ObjectMapper();
		try {
			var source = (JsonNode) objectMapper.readTree(serializer.serialize(sourceStructure).toString());
			var target = (JsonNode) objectMapper.readTree(serializer.serialize(targetStructure).toString());
			var patch = JsonDiff.asJson(source, target);
			
			result = new JSONArray(patch.toString());
			
			logger.debug("COMPARISON:" + result.write(new StringWriter(), 3, 0).toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}		
		return result;
	}

	@Override
	public JSONArray compare(IStructure sourceStructure, IStructure targetStructure, ISerializer serializer, String fileToSave) {
		var result = this.compare(sourceStructure, targetStructure, serializer);
		
		var fileName = config.get(Config.DEFAULT_COMPARATOR_PATH) + fileToSave;
		try {
			Files.write(Paths.get(fileName), result.write(new StringWriter(), 3, 0).toString().getBytes());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

}
