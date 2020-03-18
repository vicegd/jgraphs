package jgraphs.core.participant;

public interface IParticipantManager {
    public IParticipantManager createNewParticipantManager();
	
    public int getParticipant();

    public void setParticipant(int participant);

    public int getOpponent();
	
    public void nextParticipant();
    
    public int getNumberOfParticipants();
}
