package alphastar.core.structure;

import java.util.List;
import java.util.UUID;

import alphastar.node.INode;

public interface IState {
	public UUID getId();
	
	public IBoard getBoard();
	
	public String getStateValuesToHTML();

    public void setBoard(IBoard board);
    
    public EPlayer getPlayer();

    public void setPlayer(EPlayer playerNo);

    public EPlayer getOpponent();
    
    public int getVisitCount();

    public void setVisitCount(int visitCount);

    public double getWinScore();

    public void setWinScore(double winScore);
    
    public INode getNode();
    
    public void setNode(INode node);

    public List<IState> getAllPossibleStates();

    public void incrementVisit();

    public void addScore(double score);

    public void randomPlay();

    public void togglePlayer();
}