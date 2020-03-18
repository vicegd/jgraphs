package jgraphs.core.serialization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.core.node.INode;
import jgraphs.core.situation.ISituation;
import jgraphs.core.state.IState;
import jgraphs.core.structure.IStructure;
import jgraphs.core.structure.Tree;
import jgraphs.core.utils.IllegalTreeOperationException;
import jgraphs.core.utils.Utils;

public abstract class AbstractSerializer implements ISerializer {
	private static Logger log = LoggerFactory.getLogger(AbstractSerializer.class);
	
	@Override
	public JSONArray serialize(IStructure structure, String fileName) {
		var json = new JSONArray();				
		this.iterateStructure(structure, structure.getFirst(), json);
		
		if (fileName != null) {
			try {
				Files.write(Paths.get(fileName), json.toString().getBytes());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		
		log.info(json.toString());
		return json;
	}
	
	@Override
	public JSONArray serialize(IStructure structure) {
		return this.serialize(structure, null);
	}
	
	@Override
	public IStructure deserialize(String fileName) {
		try {
			Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public IStructure deserialize(JSONArray json) {
		var structure = Utils.getInstance().createGraphInstance();
		System.out.println(structure.getNodeList().size());
		
		json.forEach(element -> {
			var nodeObject = (JSONObject)element;
			var node = Utils.getInstance().createNodeInstance();			
			node.setId((UUID)nodeObject.get("id"));
			
			structure.addNode(node);
		});
		System.out.println(structure.getNodeList().size());
		
		return structure;
	}
	
	private void iterateStructure(IStructure structure, INode node, JSONArray json) {
		var objectNode = new JSONObject();
		var objectState = new JSONObject();
		var objectSituation = new JSONObject();
				
		this.putNodeInfo(structure, node, objectNode);
		this.putStateInfo(node.getState(), objectState);
		this.putSituationInfo(node.getState().getSituation(), objectSituation);

		objectNode.put("state", objectState);
		objectState.put("situation", objectSituation);
		json.put(objectNode);
		for (var n : node.getSuccessors()) {
			iterateStructure(structure, n, json);
		}
	}

	private void putNodeInfo(IStructure structure, INode node, JSONObject objectNode) {
		objectNode.put("id", node.getId());
		objectNode.put("name", structure.getNodeName(node.getId()));
		var predecessors = new JSONArray();
		for (var n : node.getPredecessors()) {
			predecessors.put(n.getId());
		}
		objectNode.put("predecessors", predecessors);
		var successors = new JSONArray();
		for (var n : node.getSuccessors()) {
			successors.put(n.getId());
		}
		objectNode.put("successors", successors);
	}
	
	private void putStateInfo(IState state, JSONObject objectState) {
		objectState.put("id", state.getId());
		objectState.put("visits", state.getVisitCount());	
		var scores = new JSONArray();
		for (var i = 1; i <= state.getParticipantManager().getNumberOfParticipants(); i++) {
			scores.put(state.getScore(i));
		}
		objectState.put("scores", scores);
		objectState.put("totalScores", state.getTotalScores());
		objectState.put("currentParticipant", state.getParticipantManager().getParticipant());
	}
	
	protected abstract void putSituationInfo(ISituation situation, JSONObject objectSituation);
}
