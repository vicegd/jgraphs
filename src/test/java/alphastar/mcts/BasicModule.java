package alphastar.mcts;

import com.google.inject.AbstractModule;

import alphastar.core.State;
import alphastar.core.structure.IState;
import alphastar.node.INode;
import alphastar.node.Node;

public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
        //bind(IState.class).to(State.class);
    	bind(INode.class).toInstance(new Node());
    	bind(IState.class).toInstance(new State());
    }
}