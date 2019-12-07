package jgraphs.core.state;

import java.util.List;
import java.util.UUID;

import jgraphs.core.node.INode;
import jgraphs.core.participant.IParticipantManager;
import jgraphs.core.situation.ISituation;

public interface IState {
    public IState createNewState();
	
	public UUID getId();
	
	public ISituation getSituation();
	
	public String getStateValuesToHTML();

    public void setSituation(ISituation board);
    
    public IParticipantManager getParticipantManager();

    public void setParticipantManager(IParticipantManager playerManager);
    
    public int getVisitCount();

    public void setVisitCount(int visitCount);

    public double getScore(int player);
    
    public double getTotalScores();

    public void setScore(int player, double score);
    
    public INode getNode();
    
    public void setNode(INode node);

    public List<IState> getAllPossibleStates();
    
    public List<IState> getNextStates();

    public void incrementVisit();

    public void addScore(int player, double score);

    public void randomMovement();

    public void nextParticipant();
    
    public String serializeScores();
}