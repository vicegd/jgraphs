package jgraphs.core.persistence;

import jgraphs.core.serialization.ISerializer;
import jgraphs.core.structure.IStructure;

public interface IPersistence {
	public void saveStructure(String key, ISerializer serializer, IStructure structure);
	public IStructure loadStructure(String key, ISerializer serializer);
}
