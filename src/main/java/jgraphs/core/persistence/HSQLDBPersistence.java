package jgraphs.core.persistence;

import jgraphs.core.serialization.ISerializer;
import jgraphs.core.structure.IStructure;

public class HSQLDBPersistence implements IPersistence {
	@Override
	public void saveStructure(String key, ISerializer serializer, IStructure structure) {
        serializer.serialize(structure, "persistence/" + key + ".json");  
	}

	@Override
	public IStructure loadStructure(String key, ISerializer serializer) {
		return serializer.deserialize("persistence/" + key + ".json");  
	}
}
