package jgraphs.subsystem.comparator;

import org.json.JSONArray;

import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.serialization.ISerializer;

public interface IComparator {
	public JSONArray compare(IStructure sourceStructure, IStructure targetStructure, ISerializer serializer);
	public JSONArray compare(IStructure sourceStructure, IStructure targetStructure, ISerializer serializer, String fileToSave);
}
