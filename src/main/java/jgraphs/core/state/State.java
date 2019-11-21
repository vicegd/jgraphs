package jgraphs.core.state;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

import jgraphs.core.board.IBoard;
import jgraphs.core.node.INode;
import jgraphs.core.status.EPlayer;

public class State implements IState {
	private UUID id;
	private INode node;
	private IBoard board;
	private EPlayer player;
	private int visitCount;
	private double score;

	@Inject
    public State(IBoard board) {
    	this.id = UUID.randomUUID();
        this.visitCount = 0;
        this.score = 0;
    	this.board = board.createNewBoard();
        this.player = EPlayer.None;
    }
   
    @Override
    public IState createNewState() {
    	var copy = new State(this.board);
    	copy.player = this.getPlayer();
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
        values.append("<br/>score:" + this.getScore());
        values.append("<br/>visits:" + this.getVisitCount());
        return values.toString();	        		
    }

    @Override
    public void setBoard(IBoard board) {
    	this.board = board;
    }

    @Override
    public EPlayer getPlayer() {
        return this.player;
    }

    @Override
    public void setPlayer(EPlayer player) {
        this.player = player;
    }

    @Override
    public EPlayer getOpponent() {
    	switch (this.player) {
    		case None:
    			return EPlayer.P1;
    		case P1:
    			return EPlayer.P2;
    		case P2:
    			return EPlayer.P1;
    		default:
    			return EPlayer.None;
    	}
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
    public double getScore() {
        return this.score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
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
            newState.setPlayer(this.getOpponent());
            newState.getBoard().performMove(newState.getPlayer(), p);
            possibleStates.add(newState);
        });
        return possibleStates;
    }

    @Override
    public void incrementVisit() {
        this.visitCount++;
    }

    @Override
    public void addScore(double score) {
    	this.score += score;
    }

    @Override
    public void randomPlay() {
        var availablePositions = this.board.getEmptyPositions();
        var selectRandom = (int) (Math.random() * availablePositions.size());
        this.board.performMove(this.player, availablePositions.get(selectRandom));
    }

    @Override
    public void togglePlayer() {
        this.player = this.getOpponent();
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("State:\n");
    	sb.append("\tPlayer: \t" + this.player + "\n"); 
    	sb.append("\tVisitCount: \t" + this.visitCount + "\n"); 
    	sb.append("\tWinScore: \t" + this.score + "\n"); 
    	sb.append(this.board.toString());
        return sb.toString();
    }
}
