package jgraphs.core.participant;

public class SingleParticipantManager extends AbstractParticipantManager {
	private SingleParticipantManager() {
		this.nParticipants = 1;
	}
	
	@Override
	public IParticipantManager getInstance() {
		if (instance == null)
    		instance = new SingleParticipantManager();
    	return instance; 
	}

	@Override
	public int getOpponent() {
    	return 0;
	}

	@Override
	public int getNumberOfParticipants() {
		return this.nParticipants;
	}

}
