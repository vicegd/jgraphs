package jgraphs.core.situation;

import java.util.List;

public interface ISituation {
    public ISituation createNewSituation();
    
    public List<ISituation> nextSituations();

	public List<ISituation> nextSituations(int participant, Object value);

	public int checkStatus();
	
	public String getValuesToString();
	
	public String getValuesToHTML();
}