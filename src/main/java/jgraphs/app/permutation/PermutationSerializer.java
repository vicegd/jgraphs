package jgraphs.app.permutation;

import org.json.JSONObject;

import jgraphs.core.serialization.AbstractSerializer;
import jgraphs.core.situation.ISituation;

public class PermutationSerializer extends AbstractSerializer {
	@Override
	public void putSituationInfo(ISituation situation, JSONObject objectSituation) {
		var permutationSituation = (PermutationSituation)situation;
		objectSituation.put("n", permutationSituation.getSize());
		objectSituation.put("level", permutationSituation.getLevel());
		objectSituation.put("values", permutationSituation.getValues());
		objectSituation.put("used", permutationSituation.getUsed());
	}
}
