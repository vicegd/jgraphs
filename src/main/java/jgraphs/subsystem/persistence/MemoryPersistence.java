package jgraphs.subsystem.persistence;

import java.util.HashMap;

import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.serialization.ISerializer;

public class MemoryPersistence implements IPersistence {
	public static HashMap<String, IStructure> structures = new HashMap<String, IStructure>();
	
	@Override
	public void saveStructure(String key, ISerializer serializer, IStructure structure) {
		structures.put(key, structure);
	}
	
	@Override
	public IStructure loadStructure(String key, ISerializer serializer) {
		return structures.get(key);
	}
	
}
