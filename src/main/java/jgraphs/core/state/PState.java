package jgraphs.core.state;

import com.google.inject.Inject;

import jgraphs.core.participant.IParticipantManager;

public class PState extends AbstractState {

	@Inject
    public PState(IParticipantManager participantManager) {
    	super(participantManager);
    }
   
    @Override
    public IState createNewState() {
    	var copy = new PState(this.participantManager);
    	copy.node = this.node;
    	if (this.situation != null)
    		copy.situation = this.situation.createNewSituation();
        return copy;  
    }
}