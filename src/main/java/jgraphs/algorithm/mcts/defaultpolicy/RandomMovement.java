package jgraphs.algorithm.mcts.defaultpolicy;

import jgraphs.core.node.INode;

public class RandomMovement implements IDefaultPolicy {

	@Override
	public int simulation(INode node) {
        var tempNode = node.createNewNode();
        var tempState = tempNode.getState();
        var situationStatus = tempState.getSituation().checkStatus();

        while (situationStatus == -1) { //IN PROGRESS
            tempState = tempState.randomNextState();
            situationStatus = tempState.getSituation().checkStatus();
        }

        return situationStatus; //0 draw or a number to indicate the winner player
	}

}
