package jgraphs.app.tictactoe;

import java.util.Collections;

public class TicTacToePSituation extends TicTacToeSituation {
    public TicTacToePSituation() {
    	super();
    	super.situations = Collections.synchronizedList(super.situations);
    }
    
    public TicTacToePSituation(int n) {
    	super(n);
    	super.situations = Collections.synchronizedList(super.situations);
    }
}