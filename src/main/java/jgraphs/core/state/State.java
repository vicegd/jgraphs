package jgraphs.core.state;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

import jgraphs.core.board.IBoard;
import jgraphs.core.node.INode;
import jgraphs.core.player.IPlayerManager;

public class State implements IState {
	private UUID id;
	private INode node;
	private IBoard board;
	private IPlayerManager playerManager;
	private int visitCount;
	private double[] scores;

	@Inject
    public State(IBoard board, IPlayerManager playerManager) {
    	this.id = UUID.randomUUID();
        this.visitCount = 0;
    	this.board = board.createNewBoard();
    	this.playerManager = playerManager.createNewPlayerManager();
    	this.scores = new double[this.playerManager.getNumberOfPlayers()];
    }
   
    @Override
    public IState createNewState() {
    	var copy = new State(this.board, this.playerManager);
    	copy.node = node;
        return copy;  
    }

    @Override
    public UUID getId() {
    	return this.id;
    }
    
    @Override
    public IBoard getBoard() {
        return this.board;
    }
    
    @Override
    public String getStateValuesToHTML() {
        var values = new StringBuilder();
        values.append("<br/>player:" + this.getPlayerManager().getPlayer());
        values.append("<br/>scores:" + this.serializeScores());
        values.append("<br/>visits:" + this.getVisitCount());
        return values.toString();	        		
    }

    @Override
    public void setBoard(IBoard board) {
    	this.board = board;
    }

    @Override
    public IPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    @Override
    public void setPlayerManager(IPlayerManager player) {
        this.playerManager = player;
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
    public double getScore(int player) {
        return this.scores[player-1];
    }

    @Override
    public void setScore(int player, double score) {
        this.scores[player-1] = score;
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
    public List<IState> getAllPossibleStates() {
        List<IState> possibleStates = new ArrayList<>();
        var availablePositions = this.board.getEmptyPositions();
        availablePositions.forEach(p -> { 
            var newState = this.createNewState();
            if (newState.getBoard().checkStatus() == -1) { //still in progress
                newState.getPlayerManager().setPlayer(this.getPlayerManager().getOpponent());
            	newState.getBoard().performMove(newState.getPlayerManager().getPlayer(), p);
            	possibleStates.add(newState);
            }
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
    public void randomPlay() {
        var availablePositions = this.board.getEmptyPositions();
        var selectRandom = (int) (Math.random() * availablePositions.size());
        this.board.performMove(this.playerManager.getPlayer(), availablePositions.get(selectRandom));
    }

    @Override
    public void togglePlayer() {
        this.playerManager.togglePlayer();
    }
    
    @Override
    public String toString() {
    	var sb = new StringBuilder();
    	sb.append("State:\n");
    	sb.append("\tPlayer: \t" + this.playerManager.getPlayer() + "\n"); 
    	sb.append("\tVisitCount: \t" + this.visitCount + "\n"); 
    	sb.append("\tWinScore: \t" + this.serializeScores() + "\n"); 
    	sb.append(this.board.toString());
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
