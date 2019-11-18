package jgraphs.utils;

import com.google.inject.AbstractModule;

import jgraphs.node.INode;
import jgraphs.node.Node;
import jgraphs.tree.ITree;
import jgraphs.tree.Tree;
import jpgrahs.state.IState;
import jpgrahs.state.State;

public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
    	bind(ITree.class).to(Tree.class);
    	bind(INode.class).to(Node.class);
    	bind(IState.class).to(State.class);
    }
}