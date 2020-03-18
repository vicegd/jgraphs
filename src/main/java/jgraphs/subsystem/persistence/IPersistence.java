package jgraphs.subsystem.persistence;

import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.serialization.ISerializer;

public interface IPersistence {
	public void saveStructure(String key, ISerializer serializer, IStructure structure);
	public IStructure loadStructure(String key, ISerializer serializer);
}
