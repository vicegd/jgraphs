package jgraphs.subsystem.serialization;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import org.json.JSONArray;
import org.json.JSONObject;

import jgraphs.core.node.INode;
import jgraphs.core.situation.ISituation;
import jgraphs.core.state.IState;
import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.utils.Dependency;

public abstract class AbstractSerializer implements ISerializer {
	protected static final ILogger logger = new DefaultLogger(AbstractSerializer.class);
	protected ISituation situation;
	
	@Override
	public JSONArray serialize(IStructure structure) {
		var json = new JSONArray();				
			
		for (var node : structure.getNodeList()) {
			var nodeObject = new JSONObject();
			var stateObject = new JSONObject();
			
			this.serializeNode(structure, node, nodeObject);
			this.serializeState(node.getState(), stateObject);
			var situationObject = this.serializeSituation(node.getState().getSituation());

			nodeObject.put("state", stateObject);
			stateObject.put("situation", situationObject);
			json.put(nodeObject);
		}
		
		logger.debug("SERIALIZATION:" + json.write(new StringWriter(), 3, 0).toString());
		return json;
	}
	
	@Override
	public IStructure deserialize(JSONArray json) {
		var structure = Dependency.getInstance().createTreeInstance();
		var nodeNames = new HashMap<UUID, String>();
		var nodes = new HashMap<UUID, INode>();
		var nodeList = new ArrayList<INode>();
				
		json.forEach(element -> { //base
			var nodeObject = (JSONObject)element;
			var stateObject = (JSONObject)nodeObject.get("state");
			var situationObject = (JSONObject)stateObject.get("situation");
			
			var node = Dependency.getInstance().createNodeInstance();			
			var state = Dependency.getInstance().createStateInstance();	
					
			node.setId(UUID.fromString((String)nodeObject.get("id")));
			node.setState(state);
		
			state.setId(UUID.fromString((String)stateObject.get("id")));
			state.setVisitCount((Integer)stateObject.get("visits"));
			state.getParticipantManager().setParticipant((Integer)stateObject.get("currentParticipant"));
			state.setScores(fromJSONArrayToDoubleArray(stateObject.get("scores")));
				
			var situation = this.deserializeSituation(situationObject);
			state.setSituation(situation);
			
			nodeNames.put(node.getId(), (String)nodeObject.get("name"));
			nodes.put(node.getId(), node);
			nodeList.add(node);
		});
		
		json.forEach(element -> { //predecessors and successors
			var nodeObject = (JSONObject)element;	
			var node = nodes.get(UUID.fromString((String)nodeObject.get("id")));
			
			var predecessors = (JSONArray)nodeObject.get("predecessors");
			var successors = (JSONArray)nodeObject.get("successors");
			
			predecessors.forEach(predecessorElement -> {
				var predecessorNode = nodes.get(UUID.fromString((String)predecessorElement));
				node.getPredecessors().add(predecessorNode);
			});
			
			successors.forEach(successorElement -> {
				var successorNode = nodes.get(UUID.fromString((String)successorElement));
				node.getSuccessors().add(successorNode);
			});
		});
		
		try {
			structure.loadStructure(nodeNames, nodes, nodeList);
		} catch (OperationNotSupportedException e) {
			logger.error(e.getMessage());
		}	
		return structure;
	}
	
	private void serializeNode(IStructure structure, INode node, JSONObject objectNode) {
		objectNode.put("id", node.getId().toString());
		objectNode.put("name", structure.getNodeName(node.getId()));
		var predecessors = new JSONArray();
		for (var n : node.getPredecessors()) {
			predecessors.put(n.getId().toString());
		}
		objectNode.put("predecessors", predecessors);
		var successors = new JSONArray();
		for (var n : node.getSuccessors()) {
			successors.put(n.getId().toString());
		}
		objectNode.put("successors", successors);
	}
	
	private void serializeState(IState state, JSONObject objectState) {
		objectState.put("id", state.getId().toString());
		objectState.put("visits", state.getVisitCount());	
		var scores = new JSONArray();
		for (var i = 1; i <= state.getParticipantManager().getNumberOfParticipants(); i++) {
			scores.put(state.getScore(i));
		}
		objectState.put("scores", scores);
		objectState.put("currentParticipant", state.getParticipantManager().getParticipant());
	}
	
	protected static int[] fromJSONArrayToIntArray(Object object) {
		var arrayObject = (JSONArray)object;
		int[] result = new int[arrayObject.length()];
		for (var i = 0; i < arrayObject.length(); i++) 
			result[i] = arrayObject.getInt(i);		
		return result;
	}
	
	protected static int[][] fromJSONTableToIntTable(Object object) {
		var tableObject = (JSONArray)object;
		int[][] result = new int[tableObject.length()][tableObject.length()];
		for (var i = 0; i < tableObject.length(); i++) {
			var arrayObject = (JSONArray)tableObject.get(i);
			for (var j = 0; j < tableObject.length(); j++) {
				result[i][j] = arrayObject.getInt(j);			
			}
		}	
		return result;
	}
	
	protected static double[] fromJSONArrayToDoubleArray(Object object) {
		var arrayObject = (JSONArray)object;
		double[] result = new double[arrayObject.length()];
		for (var i = 0; i < arrayObject.length(); i++) 
			result[i] = arrayObject.getDouble(i);		
		return result;
	}
	
	protected static boolean[] fromJSONArrayToBooleanArray(Object object) {
		var arrayObject = (JSONArray)object;
		boolean[] result = new boolean[arrayObject.length()];
		for (var i = 0; i < arrayObject.length(); i++) 
			result[i] = arrayObject.getBoolean(i);		
		return result;
	}
	
	protected abstract JSONObject serializeSituation(ISituation situation);
	protected abstract ISituation deserializeSituation(JSONObject objectSituation);
}
