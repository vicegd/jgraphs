package jgraphs.core.player;

public class TwoPlayerManager extends AbstractPlayerManager {
	public TwoPlayerManager() {
		this.nPlayers = 2;
	}
	
	
	@Override
	public IPlayerManager createNewPlayerManager() {
    	var copy = new TwoPlayerManager();
    	copy.player = this.player;
        return copy;
	}

	@Override
	public int getOpponent() {
    	switch (this.player) {
			case 0: 
				return 1; 
			case 1:
				return 2;
			case 2:
				return 1;
			default:
				return 0;
    	}
	}

	@Override
	public int getNumberOfPlayers() {
		return this.nPlayers;
	}

}
