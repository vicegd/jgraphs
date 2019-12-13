package jgraphs.app.permutation;

import jgraphs.core.participant.IParticipantManager;
import jgraphs.core.participant.SingleParticipantManager;
import jgraphs.utils.BasicModule;

public class PermutationModule extends BasicModule {
    @Override
    protected void configure() {
    	super.configure();
    	bind(IParticipantManager.class).to(SingleParticipantManager.class);
    }
}