package jgraphs.core.board;

import java.util.List;

import alphastar.core.structure.EGameStatus;
import alphastar.core.structure.EPlayer;
import jgraphs.core.status.Position;

public interface IBoard {
    public IBoard createNewBoard();
	
	public int getTotalMoves();
	
	public int[][] getBoardValues();
	
	public String getBoardValuesToHTML();

	public void setBoardValues(int[][] boardValues);

	public List<Position> getEmptyPositions();
	
	public void performMove(EPlayer player, Position p);
	
	public EGameStatus checkStatus();
}