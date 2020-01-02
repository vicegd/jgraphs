package jgraphs.app.permutation;

import java.util.Collections;

public class PermutationPSituation extends PermutationSituation {
	public PermutationPSituation() {
		super();
		super.situations = Collections.synchronizedList(super.situations);
	}
	
    public PermutationPSituation(int n) {
    	super(n);
    	super.situations = Collections.synchronizedList(super.situations);
    }
  
}
