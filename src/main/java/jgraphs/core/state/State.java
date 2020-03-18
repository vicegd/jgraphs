package jgraphs.core.state;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.participant.IParticipantManager;
import jgraphs.core.situation.ISituation;

public class State implements IState {
	protected UUID id;
	protected INode node;
	protected ISituation situation;
	protected IParticipantManager participantManager;
	protected boolean beingExplored;
	protected int visitCount;
	protected double[] scores;

	@Inject
    public State(IParticipantManager participantManager) {
    	this.id = UUID.randomUUID();
    	this.beingExplored = false;
        this.visitCount = 0; //visits = 0;    	
    	this.participantManager = participantManager.getInstance();
    	this.scores = new double[this.participantManager.getNumberOfParticipants()]; //scores = 0
    }
   
    @Override
    public IState createNewState() {
    	var copy = new State(this.participantManager);
    	copy.node = this.node;
    	if (this.situation != null)
    		copy.situation = this.situation.createNewSituation();
        return copy;  
    }

    @Override
    public UUID getId() {
    	return this.id;
    }
    
	@Override
	public void setId(UUID id) {
		this.id = id;	
	}
    
    @Override
    public ISituation getSituation() {
        return this.situation;
    }
    
    @Override
    public String getStateValuesToHTML() {
        var values = new StringBuilder();
        values.append("<br/>participant:" + this.getParticipantManager().getParticipant());
        values.append("<br/>scores:" + this.serializeScores());
        values.append("<br/>visits:" + this.getVisitCount());
        return values.toString();	        		
    }

    @Override
    public void setSituation(ISituation situation) {
    	this.situation = situation;
    }

    @Override
    public IParticipantManager getParticipantManager() {
        return this.participantManager;
    }

    @Override
    public void setParticipantManager(IParticipantManager participantManager) {
        this.participantManager = participantManager;
    }
    
	@Override
	public boolean getBeingExplored() {
		return this.beingExplored;
	}

	@Override
	public void setBeingExplored(boolean beingExplored) {
		this.beingExplored = beingExplored;
	}

    @Override
    public int getVisitCount() {
        return this.visitCount;
    }

    @Override
    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    @Override
    public double getScore(int participant) {
        return this.scores[participant-1];
    }
    
    @Override
    public double getTotalScores() {
    	var total = 0.0;
    	for (var i = 0; i < scores.length; i++) {
    		total += scores[i];
    	}
    	return total;
    }

    @Override
    public void setScore(int participant, double score) {
        this.scores[participant-1] = score;
    }
    
	@Override
	public void setScores(double[] scores) {
		this.scores = scores;
	}
    
	@Override
	public INode getNode() {
		return this.node;
	}
	
	@Override
	public void setNode(INode node) {
		this.node = node;
	}
    
    @Override
    public List<IState> nextStates() {
        List<IState> possibleStates = new ArrayList<>();
        var nextParticipant = this.getParticipantManager().getOpponent();
        var availableSituations = this.situation.nextSituations(nextParticipant, null);
        availableSituations.forEach(s -> { 
            var newState = this.createNewState();
            newState.getParticipantManager().setParticipant(nextParticipant);
            newState.setSituation(s);
            possibleStates.add(newState);
        });
        return possibleStates;
    }

    @Override
    public void incrementVisit() {
        this.visitCount++;
        this.beingExplored = false;
    }

    @Override
    public void addScore(int player, double score) {
   		this.scores[player-1] += score;
    }

    @Override
    public IState randomNextState() {
    	var states = nextStates();
    	var selectRandom = (int)(Math.random() * states.size());
    	return states.get(selectRandom);
    }

    @Override
    public void nextParticipant() {
        this.participantManager.nextParticipant();
    }
    
    @Override
    public String serializeScores() {
    	var sb = new StringBuilder();
    	sb.append("[");
    	for (double score : this.scores) {
    		sb.append(score + " ");
    	}
    	sb.append("]");
    	return sb.toString();
    }
    
    @Override
    public String toString() {
    	var sb = new StringBuilder();
    	sb.append("State:\n");
    	sb.append("\tId: \t\t" + this.getId() + "\n"); 
    	sb.append("\tParticipant: \t" + this.participantManager.getParticipant() + "\n"); 
    	sb.append("\tVisitCount: \t" + this.visitCount + "\n"); 
    	sb.append("\tWinScore: \t" + this.serializeScores() + "\n"); 
    	sb.append(this.situation.toString());
        return sb.toString();
    }
    
	@Override
	public boolean equals(Object obj) {
		var that = (State)obj;
		if (!this.id.equals(that.id)) return false;
		if (!this.node.getId().equals(that.node.getId())) return false;
		if (!this.situation.equals(that.situation)) return false;
		if (this.participantManager.getNumberOfParticipants() != that.participantManager.getNumberOfParticipants()) return false;
		if (this.participantManager.getParticipant() != that.participantManager.getParticipant()) return false;
		if (this.participantManager.getOpponent() != that.participantManager.getOpponent()) return false;
		if (this.visitCount != that.visitCount) return false;
		if (!this.serializeScores().equals(that.serializeScores())) return false;
        return true;        
	}
   
}
