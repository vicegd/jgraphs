package jgraphs.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.JSONArray;

import jgraphs.core.structure.IStructure;
import jgraphs.serialization.ISerializer;
import jgraphs.utils.Config;
import jgraphs.utils.Logger;

public class FilePersistence implements IPersistence {
	private static org.slf4j.Logger log = Logger.getInstance().getLogger(FilePersistence.class);
	private HashMap<String, String> config;
	
	public FilePersistence() {
		this.config = Config.getInstance().getConfig("filePersistence");
	}
	
	@Override
	public void saveStructure(String key, ISerializer serializer, IStructure structure) {
		var json = serializer.serialize(structure);
		var fileName = this.config.get("persistence_path") + key + ".json";
		try {
			Files.write(Paths.get(fileName), json.toString().getBytes());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public IStructure loadStructure(String key, ISerializer serializer) {
		String content = "";
		var fileName = this.config.get("persistence_path") + key + ".json";
		try {
			content = Files.readString(Paths.get(fileName));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		var json = new JSONArray(content);
		return serializer.deserialize(json);
	}
		
}
