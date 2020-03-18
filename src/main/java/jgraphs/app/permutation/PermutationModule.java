package jgraphs.app.permutation;

import jgraphs.core.node.IMaxValueNode;
import jgraphs.core.node.ScoreMaxValueNode;
import jgraphs.core.participant.IParticipantManager;
<<<<<<< master
import jgraphs.core.participant.SingleParticipantManager;
import jgraphs.utils.module.DefaultModuleConfiguration;
import jgraphs.utils.module.EModuleConfiguration;
=======
import jgraphs.core.participant.SinglearticipantManager;
import jgraphs.core.utils.BasicModule;
>>>>>>> 16cccd1 Persistence API finished

public class PermutationModule extends DefaultModuleConfiguration {
    public PermutationModule(EModuleConfiguration moduleConfiguration) {
		super(moduleConfiguration);
	}

	@Override
    protected void configure() {
    	super.configure();
<<<<<<< master
    	bind(IParticipantManager.class).to(SingleParticipantManager.class);
    	bind(IMaxValueNode.class).to(ScoreMaxValueNode.class);
=======
    	bind(IParticipantManager.class).to(SinglearticipantManager.class);
>>>>>>> 16cccd1 Persistence API finished
    }
}