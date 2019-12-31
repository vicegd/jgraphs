package jgraphs.utils.module;

import com.google.inject.AbstractModule;

import jgraphs.core.node.INode;
import jgraphs.core.node.PNode;
import jgraphs.core.state.IState;
import jgraphs.core.state.PState;
import jgraphs.core.structure.graph.IGraph;
import jgraphs.core.structure.graph.PGraph;
import jgraphs.core.structure.tree.ITree;
import jgraphs.core.structure.tree.PTree;

public class PBasicModule extends AbstractModule {
    @Override
    protected void configure() {
    	bind(ITree.class).to(PTree.class);
    	bind(IGraph.class).to(PGraph.class);
    	bind(INode.class).to(PNode.class);
    	bind(IState.class).to(PState.class);
    }
}