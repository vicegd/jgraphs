package jgraphs.utils.module;

import com.google.inject.AbstractModule;

import jgraphs.core.node.INode;
import jgraphs.core.node.Node;
import jgraphs.core.state.IState;
import jgraphs.core.state.State;
import jgraphs.core.structure.graph.IGraph;
import jgraphs.core.structure.graph.SilentGraph;
import jgraphs.core.structure.tree.ITree;
import jgraphs.core.structure.tree.SilentTree;

public class SilentBasicModule extends AbstractModule {
    @Override
    protected void configure() {
    	bind(ITree.class).to(SilentTree.class);
    	bind(IGraph.class).to(SilentGraph.class);
    	bind(INode.class).to(Node.class);
    	bind(IState.class).to(State.class);
    }
}