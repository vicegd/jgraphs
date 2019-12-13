package jgraphs.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.JSONArray;
import org.slf4j.Logger;

import jgraphs.core.structure.IStructure;
import jgraphs.logging.Logging;
import jgraphs.serialization.ISerializer;
import jgraphs.utils.Config;

public class FilePersistence implements IPersistence {
	private static HashMap<String, String> config = Config.getInstance().getConfig(FilePersistence.class);
	private static Logger log = Logging.getInstance().getLogger(FilePersistence.class);
	
	@Override
	public void saveStructure(String key, ISerializer serializer, IStructure structure) {
		var json = serializer.serialize(structure);
		var fileName = config.get("persistence_path") + key + ".json";
		try {
			Files.write(Paths.get(fileName), json.toString().getBytes());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public IStructure loadStructure(String key, ISerializer serializer) {
		String content = "";
		var fileName = config.get("persistence_path") + key + ".json";
		try {
			content = Files.readString(Paths.get(fileName));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		var json = new JSONArray(content);
		return serializer.deserialize(json);
	}
		
}
