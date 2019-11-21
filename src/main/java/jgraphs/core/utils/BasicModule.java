package jgraphs.core.utils;

import com.google.inject.AbstractModule;

import jgraphs.core.board.IBoard;
import jgraphs.core.node.INode;
import jgraphs.core.node.Node;
import jgraphs.core.state.IState;
import jgraphs.core.state.State;
import jgraphs.core.tree.ITree;
import jgraphs.core.tree.Tree;
import jgraphs.game.tictactoe.TicTacToeBoard;

public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
    	bind(ITree.class).to(Tree.class);
    	bind(INode.class).to(Node.class);
    	bind(IState.class).to(State.class);
    	bind(IBoard.class).to(TicTacToeBoard.class);
    }
}