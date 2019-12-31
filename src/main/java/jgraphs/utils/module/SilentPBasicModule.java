package jgraphs.utils.module;

import com.google.inject.AbstractModule;

import jgraphs.core.node.INode;
import jgraphs.core.node.PNode;
import jgraphs.core.state.IState;
import jgraphs.core.state.PState;
import jgraphs.core.structure.graph.IGraph;
import jgraphs.core.structure.graph.SilentPGraph;
import jgraphs.core.structure.tree.ITree;
import jgraphs.core.structure.tree.SilentPTree;

public class SilentPBasicModule extends AbstractModule {
    @Override
    protected void configure() {
    	bind(ITree.class).to(SilentPTree.class);
    	bind(IGraph.class).to(SilentPGraph.class);
    	bind(INode.class).to(PNode.class);
    	bind(IState.class).to(PState.class);
    }
}