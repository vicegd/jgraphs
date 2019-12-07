package jgraphs.app.permutation;

import jgraphs.core.participant.IParticipantManager;
import jgraphs.core.participant.OneParticipantsManager;
import jgraphs.core.situation.ISituation;
import jgraphs.core.utils.BasicModule;

public class PermutationModule extends BasicModule {
    @Override
    protected void configure() {
    	super.configure();
    	bind(IParticipantManager.class).to(OneParticipantsManager.class);
    	bind(ISituation.class).to(PermutationSituation.class);
    }
}