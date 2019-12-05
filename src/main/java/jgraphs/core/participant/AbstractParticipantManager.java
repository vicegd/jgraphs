package jgraphs.core.participant;

public abstract class AbstractParticipantManager implements IParticipantManager {
	protected int participant;
	protected int nParticipants;

	public AbstractParticipantManager() {
		this.participant = 0;
	}
	
	@Override
	public int getParticipant() {
		return this.participant;
	}

	@Override
	public void setParticipant(int participantNo) {
		this.participant = participantNo;
	}

	@Override
	public void nextParticipant() {
		this.participant = this.getOpponent();
	}

}
