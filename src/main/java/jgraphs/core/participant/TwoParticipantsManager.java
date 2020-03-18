package jgraphs.core.participant;

public class TwoParticipantsManager extends AbstractParticipantManager {
	public TwoParticipantsManager() {
		this.nParticipants = 2;
	}
	
	@Override
	public IParticipantManager createNewParticipantManager() {
    	var copy = new TwoParticipantsManager();
    	copy.participant = this.participant;
        return copy;
	}

	@Override
	public int getOpponent() {
    	switch (this.participant) {
			case 0: 
				return 1; 
			case 1:
				return 2;
			case 2:
				return 1;
			default:
				return 0;
    	}
	}

	@Override
	public int getNumberOfParticipants() {
		return this.nParticipants;
	}

}
