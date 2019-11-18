package alphastar.core.structure;

import java.util.List;

import jpgrahs.state.IState;

public interface IBoard {
    public IBoard copy();
	
	public int getTotalMoves();
	
	public int[][] getBoardValues();
	
	public String getBoardValuesToHTML();

	public void setBoardValues(int[][] boardValues);

	public List<IPosition> getEmptyPositions();
	
	public void performMove(EPlayer player, IPosition p);
	
	public EGameStatus checkStatus();
}