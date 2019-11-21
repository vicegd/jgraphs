package jgraphs.core.board;

import java.util.List;

public interface IBoard {
    public IBoard createNewBoard();
	
	public int getTotalMoves();
	
	public int[][] getBoardValues();
	
	public String getBoardValuesToHTML();

	public void setBoardValues(int[][] boardValues);

	public List<Position> getEmptyPositions();
	
	public void performMove(int player, Position p);
	
	public int checkStatus();
}