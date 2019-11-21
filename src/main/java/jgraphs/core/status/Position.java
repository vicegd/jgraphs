package jgraphs.core.status;

public class Position {
    public int x;
    public int y;
    public int z;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Position(int x, int y, int z) {
        this(z, y);
        this.z = z;
    }
}
