package alphastar.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import alphastar.core.structure.EPlayer;
import alphastar.core.structure.IBoard;
import alphastar.core.structure.IState;
import alphastar.game.tictactoe.Board;
import alphastar.node.INode;

public class State implements IState {
	UUID id;
	INode node;
    IBoard board;
    EPlayer player;
    int visitCount;
    double winScore;

    public State() {
    	this.id = UUID.randomUUID();
    	//this.node = node;
        this.board = new Board(this);
        this.player = EPlayer.None;
        this.visitCount = 0;
        this.winScore = 0;
    }

    public State(INode node, IState state) {
    	this.id = UUID.randomUUID();
    	this.node = node;
        this.board = new Board(this, state.getBoard());
        this.player = state.getPlayer();
       // this.visitCount = state.getVisitCount();
       // this.winScore = state.getWinScore();
        this.visitCount = 0;
        this.winScore = 0;
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
        values.append("<br/>score:" + this.getWinScore());
        values.append("<br/>visits:" + this.getVisitCount());
        return values.toString();	        		
    }

    @Override
    public void setBoard(IBoard board) {
    	this.board = new Board(this, board);
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
    public double getWinScore() {
        return this.winScore;
    }

    @Override
    public void setWinScore(double winScore) {
        this.winScore = winScore;
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
            var newState = new State(node, this);
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
    	this.winScore += score;
    }

    @Override
    public void randomPlay() {
        var availablePositions = this.board.getEmptyPositions();
        var totalPossibilities = availablePositions.size();
        var selectRandom = (int) (Math.random() * totalPossibilities);
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
    	sb.append("\tWinScore: \t" + this.winScore + "\n"); 
    	sb.append(this.board.toString());
        return sb.toString();
    }
}
