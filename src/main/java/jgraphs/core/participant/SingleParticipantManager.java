package jgraphs.core.participant;

public class SingleParticipantManager extends AbstractParticipantManager {
	public SingleParticipantManager() {
		this.nParticipants = 1;
	}
	
	
	@Override
	public IParticipantManager createNewParticipantManager() {
    	var copy = new SingleParticipantManager();
    	copy.participant = this.participant;
        return copy;
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
