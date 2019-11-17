package alphastar.core;

import alphastar.core.structure.IPosition;

public class Position implements IPosition {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
	public int getX() {
        return x;
    }

    @Override
	public void setX(int x) {
        this.x = x;
    }

    @Override
	public int getY() {
        return y;
    }

    @Override
	public void setY(int y) {
        this.y = y;
    }

}
