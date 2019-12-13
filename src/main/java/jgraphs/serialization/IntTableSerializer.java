package jgraphs.serialization;

import org.json.JSONArray;
import org.json.JSONObject;

import jgraphs.core.situation.ISituation;
import jgraphs.core.situation.IntTableSituation;

public class IntTableSerializer extends AbstractSerializer {
	@Override
	public JSONObject serializeSituation(ISituation situation) {
		var intTableSituation = (IntTableSituation)situation;
		var object = new JSONObject();
		object.put("n", intTableSituation.getSize());
		object.put("level", intTableSituation.getLevel());
		var table = new JSONArray();
		for (var i = 0; i < intTableSituation.getValues().length; i++) {
			var array = intTableSituation.getValues()[i];
			table.put(new JSONArray(array));
		}		
		object.put("values", table);
		return object;
	}

	@Override
	protected ISituation deserializeSituation(JSONObject object) {
		var intTableSituation = (IntTableSituation)super.situation.createNewSituation();
		intTableSituation.setSize((int)object.get("n"));
		intTableSituation.setLevel((int)object.get("level"));
		intTableSituation.setValues(super.fromJSONTableToIntTable(object.get("values")));
		return intTableSituation;
	}
}
