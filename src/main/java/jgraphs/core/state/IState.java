package jgraphs.core.state;

import java.util.List;
import java.util.UUID;

import jgraphs.core.board.IBoard;
import jgraphs.core.node.INode;

public interface IState {
    public IState createNewState();
	
	public UUID getId();
	
	public IBoard getBoard();
	
	public String getStateValuesToHTML();

    public void setBoard(IBoard board);
    
    public int getPlayer();

    public void setPlayer(int playerNo);

    public int getOpponent();
    
    public int getVisitCount();

    public void setVisitCount(int visitCount);

    public double getScore();

    public void setScore(double score);
    
    public INode getNode();
    
    public void setNode(INode node);

    public List<IState> getAllPossibleStates();

    public void incrementVisit();

    public void addScore(double score);

    public void randomPlay();

    public void togglePlayer();
}