package jgraphs.core.state;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

import jgraphs.core.node.INode;
import jgraphs.core.participant.IParticipantManager;
import jgraphs.core.situation.ISituation;

public class State implements IState {
	private UUID id;
	private INode node;
	private ISituation situation;
	private IParticipantManager participantManager;
	private int visitCount;
	private double[] scores;

	@Inject
    public State(ISituation situation, IParticipantManager participantManager) {
    	this.id = UUID.randomUUID();
        this.visitCount = 0; //visits = 0;
    	this.situation = situation.createNewSituation();
    	this.participantManager = participantManager.createNewParticipantManager();
    	this.scores = new double[this.participantManager.getNumberOfParticipants()]; //scores = 0
    }
   
    @Override
    public IState createNewState() {
    	var copy = new State(this.situation, this.participantManager);
    	copy.node = node;
        return copy;  
    }

    @Override
    public UUID getId() {
    	return this.id;
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
    public void setSituation(ISituation board) {
    	this.situation = board;
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
    public String toString() {
    	var sb = new StringBuilder();
    	sb.append("State:\n");
    	sb.append("\tParticipant: \t" + this.participantManager.getParticipant() + "\n"); 
    	sb.append("\tVisitCount: \t" + this.visitCount + "\n"); 
    	sb.append("\tWinScore: \t" + this.serializeScores() + "\n"); 
    	sb.append(this.situation.toString());
        return sb.toString();
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
}
