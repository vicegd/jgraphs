package jgraphs.app.permutation;

import org.json.JSONObject;

import jgraphs.core.situation.ISituation;
import jgraphs.serialization.IntArraySerializer;

public class PermutationSerializer extends IntArraySerializer {
	public PermutationSerializer() {
		super.situation = (ISituation) new PermutationSituation();
	}
	
	@Override
<<<<<<< master
	public JSONObject serializeSituation(ISituation situation) {
		var object = super.serializeSituation(situation);
=======
	public void putSituationInfo(ISituation situation, JSONObject objectSituation) {
>>>>>>> 16cccd1 Persistence API finished
		var permutationSituation = (PermutationSituation)situation;
<<<<<<< master
		object.put("used", new JSONArray(permutationSituation.getUsed()));
		return object;
	}

	@Override
	protected ISituation deserializeSituation(JSONObject object) {
		var permutationSituation = (PermutationSituation)super.deserializeSituation(object);
		permutationSituation.setUsed(super.fromJSONArrayToBooleanArray(object.get("used")));	
		return permutationSituation;
=======
		objectSituation.put("n", permutationSituation.getSize());
		objectSituation.put("level", permutationSituation.getLevel());
		objectSituation.put("values", permutationSituation.getValues());
		objectSituation.put("used", permutationSituation.getUsed());
>>>>>>> 16cccd1 Persistence API finished
	}
}
