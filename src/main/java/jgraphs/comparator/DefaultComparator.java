package jgraphs.comparator;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.JSONArray;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;

import jgraphs.core.structure.IStructure;
import jgraphs.logging.Logging;
import jgraphs.serialization.ISerializer;
import jgraphs.utils.Config;

public class DefaultComparator implements IComparator {
	protected static final HashMap<String, String> config = Config.getInstance().getConfig(DefaultComparator.class);
	protected static final Logger log = Logging.getInstance().getLogger(DefaultComparator.class);
	
	@Override
	public JSONArray compare(IStructure sourceStructure, IStructure targetStructure, ISerializer serializer) {
		JSONArray result = null;
		
		var objectMapper = new ObjectMapper();
		try {
			var source = (JsonNode) objectMapper.readTree(serializer.serialize(sourceStructure).toString());
			var target = (JsonNode) objectMapper.readTree(serializer.serialize(targetStructure).toString());
			var patch = JsonDiff.asJson(source, target);
			
			result = new JSONArray(patch.toString());

			log.info(result.write(new StringWriter(), 3, 0).toString());
		} catch (IOException e) {
			log.error(e.getMessage());
		}		
		return result;
	}

	@Override
	public JSONArray compare(IStructure sourceStructure, IStructure targetStructure, ISerializer serializer, String fileToSave) {
		var result = this.compare(sourceStructure, targetStructure, serializer);
		
		var fileName = config.get("persistence_path") + fileToSave;
		try {
			Files.write(Paths.get(fileName), result.write(new StringWriter(), 3, 0).toString().getBytes());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
		return result;
	}

}
