package jgraphs.app.permutation;

import org.json.JSONArray;
import org.json.JSONObject;

import jgraphs.core.serialization.AbstractSerializer;
import jgraphs.core.situation.ISituation;

public class PermutationSerializer extends AbstractSerializer {
	@Override
	public JSONObject serializeSituation(ISituation situation) {
		var permutationSituation = (PermutationSituation)situation;
		var object = new JSONObject();
		object.put("n", permutationSituation.getSize());
		object.put("level", permutationSituation.getLevel());
		object.put("values", new JSONArray(permutationSituation.getValues()));
		object.put("used", new JSONArray(permutationSituation.getUsed()));
		return object;
	}

	@Override
	protected ISituation deserializeSituation(JSONObject object) {
		var permutationSituation = new PermutationSituation(4);
		permutationSituation.setSize((int)object.get("n"));
		permutationSituation.setLevel((int)object.get("level"));
		permutationSituation.setValues(super.fromJSONArrayToIntArray(object.get("values")));
		permutationSituation.setUsed(super.fromJSONArrayToBooleanArray(object.get("used")));	
		return permutationSituation;
	}
}
