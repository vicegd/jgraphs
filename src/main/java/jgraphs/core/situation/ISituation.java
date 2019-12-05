package jgraphs.core.situation;

import java.util.List;

public interface ISituation {
    public ISituation createNewSituation();
	
	public int getTotalMovements();
	
	public int[][] getSituationValues();
	
	public String getSituationValuesToHTML();

	public void setSituationValues(int[][] situationValues);

	public List<Position> getEmptyPositions();
	
	public void performMovement(int actor, Position p);
	
	public int checkStatus();
}