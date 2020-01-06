package jgraphs.algorithm.mcts.propagation;

import java.util.HashMap;

import jgraphs.core.node.INode;
import jgraphs.utils.Config;

public class UpdateAllPropagationPolicy implements IPropagationPolicy {
	private static final HashMap<String, String> config = Config.getConfig(UpdateAllPropagationPolicy.class);
	private boolean[] trainers;
	private double winScore = 1;
	private double drawScore = 0.5;
	private double loseScore = 0;
	
	public UpdateAllPropagationPolicy() {
        this.winScore = Double.parseDouble(config.get(Config.UPDATE_ALL_PROPAGATION_POLICY_WIN_SCORE));
        this.drawScore = Double.parseDouble(config.get(Config.UPDATE_ALL_PROPAGATION_POLICY_DRAW_SCORE));
        this.loseScore = Double.parseDouble(config.get(Config.UPDATE_ALL_PROPAGATION_POLICY_LOSE_SCORE));
        var trainersValue = config.get(Config.UPDATE_ALL_PROPAGATION_POLICY_TRAINERS).split(" ");
        this.trainers = new boolean[trainersValue.length];
        for (var i = 0; i < trainersValue.length; i++) {
           	this.trainers[i] = Boolean.parseBoolean(trainersValue[i]);
        }
	}
	
    @Override
	public void propagation(INode nodeToExplore, int result) {
    	var node = nodeToExplore;
    	var numberOfPlayers = nodeToExplore.getState().getParticipantManager().getNumberOfParticipants();
        while (node != null) {
        	node.getState().incrementVisit();
        	for (var i = 1; i <= numberOfPlayers; i++) { //check all the players
        		if (this.trainers[i-1] == true) { //we update data only if we should train that player
        			if (result == i) //current player wins
        				node.getState().addScore(i, this.getWinScore());
        			else if (result == 0) //current player draws
        				node.getState().addScore(i, this.getDrawScore());
        			else //current player loses - when any other player wins 
        				node.getState().addScore(i, this.getLoseScore());
        		}
        	}
        	if (node.getPredecessors().size() > 0)
        		node = node.getPredecessors().get(0);          	
        	else node = null;
       }
    }
    
    @Override
    public double getWinScore() {
    	return this.winScore;
    }
    
    @Override
    public double getDrawScore() {
    	return this.drawScore;
    }
    
    @Override
    public double getLoseScore() {
    	return this.loseScore;
    }
    
    @Override
    public void setTrainers(boolean[] trainers) {
    	this.trainers = trainers;
    }
}
