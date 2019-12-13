package jgraphs.app.permutation;

import org.json.JSONArray;
import org.json.JSONObject;

import jgraphs.core.situation.ISituation;
import jgraphs.serialization.IntArraySerializer;

public class PermutationSerializer extends IntArraySerializer {
	public PermutationSerializer() {
		super.situation = (ISituation) new PermutationSituation();
	}
	
	@Override
	public JSONObject serializeSituation(ISituation situation) {
		var object = super.serializeSituation(situation);
		var permutationSituation = (PermutationSituation)situation;
		object.put("used", new JSONArray(permutationSituation.getUsed()));
		return object;
	}

	@Override
	protected ISituation deserializeSituation(JSONObject object) {
		var permutationSituation = (PermutationSituation)super.deserializeSituation(object);
		permutationSituation.setUsed(super.fromJSONArrayToBooleanArray(object.get("used")));	
		return permutationSituation;
	}
}
