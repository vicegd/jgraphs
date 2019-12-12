package jgraphs.core.serialization;

import org.json.JSONArray;

import jgraphs.core.structure.IStructure;

public interface ISerializer {
	public JSONArray serialize(IStructure structure);
	public IStructure deserialize(JSONArray json);
}