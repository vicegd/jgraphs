package jgraphs.algorithm.mcts.defaultpolicy;

import jgraphs.core.node.INode;

public class RandomMovement implements IDefaultPolicy {

	@Override
	public int simulation(INode node) {
        var tempNode = node.createNewNode();
        var tempState = tempNode.getState();
        var boardStatus = tempState.getBoard().checkStatus();

        while (boardStatus == -1) { //IN PROGRESS
            tempState.nextParticipant();
            tempState.randomMovement();
            boardStatus = tempState.getBoard().checkStatus();
        }

        return boardStatus; //0 draw or a number to indicate the winner player
	}

}
