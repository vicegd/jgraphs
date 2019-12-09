package jgraphs.core.participant;

public class SinglearticipantManager extends AbstractParticipantManager {
	public SinglearticipantManager() {
		this.nParticipants = 1;
	}
	
	
	@Override
	public IParticipantManager createNewParticipantManager() {
    	var copy = new SinglearticipantManager();
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
