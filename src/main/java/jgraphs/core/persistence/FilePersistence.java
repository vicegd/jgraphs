package jgraphs.core.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.core.serialization.ISerializer;
import jgraphs.core.structure.IStructure;

public class FilePersistence implements IPersistence {
	private static Logger log = LoggerFactory.getLogger(FilePersistence.class);

	public FilePersistence() {
	}
	
	@Override
	public void saveStructure(String key, ISerializer serializer, IStructure structure) {
		var json = serializer.serialize(structure);
		var fileName = "persistence/" + key + ".json";
		try {
			Files.write(Paths.get(fileName), json.toString().getBytes());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public IStructure loadStructure(String key, ISerializer serializer) {
		String content = "";
		var fileName = "persistence/" + key + ".json";
		try {
			content = Files.readString(Paths.get(fileName));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		var json = new JSONArray(content);
		return serializer.deserialize(json);
	}
		
}
