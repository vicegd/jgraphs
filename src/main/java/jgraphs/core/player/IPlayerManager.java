package jgraphs.core.player;

public interface IPlayerManager {
    public IPlayerManager createNewPlayerManager();
	
    public int getPlayer();

    public void setPlayer(int player);

    public int getOpponent();
	
    public void togglePlayer();
}
