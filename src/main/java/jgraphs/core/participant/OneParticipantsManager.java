package jgraphs.core.participant;

public class OneParticipantsManager extends AbstractParticipantManager {
	public OneParticipantsManager() {
		this.nParticipants = 1;
	}
	
	
	@Override
	public IParticipantManager createNewParticipantManager() {
    	var copy = new OneParticipantsManager();
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
