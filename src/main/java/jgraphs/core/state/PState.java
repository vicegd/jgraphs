package jgraphs.core.state;

import com.google.inject.Inject;

import jgraphs.core.participant.IParticipantManager;

public class PState extends State {
	@Inject
    public PState(IParticipantManager participantManager) {
		super(participantManager);
    }	
}
