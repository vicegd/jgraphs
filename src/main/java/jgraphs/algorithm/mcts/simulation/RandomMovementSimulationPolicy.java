package jgraphs.algorithm.mcts.simulation;

import jgraphs.core.node.INode;

public class RandomMovementSimulationPolicy implements ISimulationPolicy {

	@Override
	public int simulation(INode node) {
		node.getState().setBeingExplored(true);
        var tempNode = node.createNewNode();
        var tempState = tempNode.getState();       
        var situationStatus = tempState.getSituation().checkStatus();

        while (situationStatus == -1) { //IN PROGRESS
            tempState = tempState.randomNextState();
            while (tempState.getBeingExplored()) 
            	tempState = tempState.randomNextState();
            situationStatus = tempState.getSituation().checkStatus();
        }

        return situationStatus; //0 draw or a number to indicate the winner player
	}

}
