package jgraphs.subsystem.serialization;

import org.json.JSONArray;
import org.json.JSONObject;

import jgraphs.core.situation.IntArraySituation;
import jgraphs.subsystem.serialization.AbstractSerializer;
import jgraphs.core.situation.ISituation;

public class IntArraySerializer extends AbstractSerializer {
	@Override
	public JSONObject serializeSituation(ISituation situation) {
		var intArraySituation = (IntArraySituation)situation;
		var object = new JSONObject();
		object.put("n", intArraySituation.getSize());
		object.put("level", intArraySituation.getLevel());
		object.put("values", new JSONArray(intArraySituation.getValues()));
		return object;
	}

	@Override
	protected ISituation deserializeSituation(JSONObject object) {
		var intArraySituation = (IntArraySituation)super.situation.createNewSituation();
		intArraySituation.setSize((int)object.get("n"));
		intArraySituation.setLevel((int)object.get("level"));
		intArraySituation.setValues(super.fromJSONArrayToIntArray(object.get("values")));
		return intArraySituation;
	}
}
