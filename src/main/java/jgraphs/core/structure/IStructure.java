package jgraphs.core.structure;

<<<<<<< master
=======
import java.io.Serializable;
>>>>>>> 16cccd1 Persistence API finished
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.naming.OperationNotSupportedException;

import jgraphs.core.node.INode;
import jgraphs.core.situation.ISituation;

public interface IStructure {
	public UUID getId();
	
	public INode getFirst();
	
	public INode getLast();
	
	public INode getSecondToLast();
	
	public String getNodeName(UUID id);
	
	public INode getNode(UUID id);
	
	public List<INode> getNodeList();
	
	public void setFirstSituation(ISituation situation);
<<<<<<< master
	
	public void loadStructure(Map<UUID, String> nodeNames, Map<UUID, INode> nodes, List<INode> nodeList) throws OperationNotSupportedException;
	
	public void loadStructure(ConcurrentHashMap<UUID, String> nodeNames, ConcurrentHashMap<UUID, INode> nodes, CopyOnWriteArrayList<INode> nodeList) throws OperationNotSupportedException;
=======
>>>>>>> 16cccd1 Persistence API finished
}
