package jgraphs.core.player;

public abstract class AbstractPlayerManager implements IPlayerManager {
	protected int player;

	public AbstractPlayerManager() {
		this.player = 0;
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
	public void togglePlayer() {
		this.player = this.getOpponent();
	}

}
