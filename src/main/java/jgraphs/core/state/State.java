package jgraphs.core.state;

import com.google.inject.Inject;

import jgraphs.core.participant.IParticipantManager;

public class State extends AbstractState {

	@Inject
    public State(IParticipantManager participantManager) {
    	super(participantManager);
    }
   
    @Override
    public IState createNewState() {
    	var copy = new State(this.participantManager);
    	copy.node = this.node;
    	if (this.situation != null)
    		copy.situation = this.situation.createNewSituation();
        return copy;  
    }
}
