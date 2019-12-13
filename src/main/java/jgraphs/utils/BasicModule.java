package jgraphs.utils;

import com.google.inject.AbstractModule;

import jgraphs.core.node.INode;
import jgraphs.core.node.Node;
import jgraphs.core.state.IState;
import jgraphs.core.state.State;
import jgraphs.core.structure.Graph;
import jgraphs.core.structure.IGraph;
import jgraphs.core.structure.ITree;
import jgraphs.core.structure.Tree;

public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
    	bind(ITree.class).to(Tree.class);
    	bind(IGraph.class).to(Graph.class);
    	bind(INode.class).to(Node.class);
    	bind(IState.class).to(State.class);
    }
}