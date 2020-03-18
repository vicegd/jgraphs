package jgraphs.core.participant;

public class TwoParticipantsManager extends AbstractParticipantManager {
	private TwoParticipantsManager() {
		this.nParticipants = 2;
	}
	
	@Override
	public IParticipantManager getInstance() {
		if (instance == null)
    		instance = new TwoParticipantsManager();
    	return instance; 
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
