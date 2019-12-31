package jgraphs.app.permutation;

import jgraphs.core.node.IMaxValueNode;
import jgraphs.core.node.ScoreMaxValueNode;
import jgraphs.core.participant.IParticipantManager;
import jgraphs.core.participant.SingleParticipantManager;
import jgraphs.utils.module.BasicModule;

public class PermutationModule extends BasicModule {
    @Override
    protected void configure() {
    	super.configure();
    	bind(IParticipantManager.class).to(SingleParticipantManager.class);
    	bind(IMaxValueNode.class).to(ScoreMaxValueNode.class);
    }
}