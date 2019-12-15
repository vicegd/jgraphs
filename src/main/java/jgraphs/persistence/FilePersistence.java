package jgraphs.persistence;

import java.io.IOException;
import java.io.StringWriter;
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
	protected static final HashMap<String, String> config = Config.getInstance().getConfig(FilePersistence.class);
	protected static final Logger log = Logging.getInstance().getLogger(FilePersistence.class);
		
	@Override
	public void saveStructure(String key, ISerializer serializer, IStructure structure) {
		var json = serializer.serialize(structure);
		var fileName = config.get(Config.FILE_PERSISTENCE_PATH) + key + ".json";
		try {
			Files.write(Paths.get(fileName), json.write(new StringWriter(), 3, 0).toString().getBytes());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public IStructure loadStructure(String key, ISerializer serializer) {
		String content = "";
		var fileName = config.get(Config.FILE_PERSISTENCE_PATH) + key + ".json";
		try {
			content = Files.readString(Paths.get(fileName));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		var json = new JSONArray(content);
		var result = serializer.deserialize(json);
		return result;
	}	
}
