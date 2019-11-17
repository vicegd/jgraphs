package alphastar.core.structure;

import java.util.List;

public interface IBoard {
	public int getTotalMoves();
	
	public int[][] getBoardValues();
	
	public String getBoardValuesToHTML();

	public void setBoardValues(int[][] boardValues);
	
	public IState getState();

	public List<IPosition> getEmptyPositions();
	
	public void performMove(EPlayer player, IPosition p);
	
	public EGameStatus checkStatus();
}