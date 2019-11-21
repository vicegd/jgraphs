package jgraphs.core.player;

public class TwoPlayerManager implements IPlayerManager {
	private int player;

	public TwoPlayerManager() {
		this.player = 0;
	}
	
	@Override
	public IPlayerManager createNewPlayerManager() {
    	var copy = new TwoPlayerManager();
    	copy.player = this.player;
        return copy;
	}

	@Override
	public int getPlayer() {
		return this.player;
	}

	@Override
	public void setPlayer(int playerNo) {
		this.player = playerNo;
	}

	@Override
	public int getOpponent() {
    	switch (this.player) {
			case 0: //no player by default
				return 1; //player 1 begins
			case 1:
				return 2;
			case 2:
				return 1;
			default:
				return 0;
    	}
	}

	@Override
	public void togglePlayer() {
		this.player = this.getOpponent();
	}

}
