package jgraphs.app.permutation;

import java.util.ArrayList;
import java.util.List;

import jgraphs.core.situation.AbstractIntArraySituation;
import jgraphs.core.situation.ISituation;

public class PermutationSituation extends AbstractIntArraySituation {
	private boolean[] used;

    public PermutationSituation(int n) {
    	super(n);
    	this.used = new boolean[super.n];
    }
        
    @Override
    public ISituation createNewSituation() {
    	var copy = new PermutationSituation(super.n);
    	super.copyInfo(this, copy);
        copy.used = this.used.clone();
        return copy;
    }
    
	@Override
	public List<ISituation> nextSituations(int participant, Object value) {
		List<ISituation> situations = new ArrayList<>();
		
		if (this.level < this.n) {			
			for (var i = 0; i < this.n; i++) {
				if (!this.used[i]) {
					var situation = (PermutationSituation)this.createNewSituation();			
					situation.values[this.level] = i;
					situation.used[i] = true;
					situation.level += 1;
					situations.add(situation);
				}
			}
		}
		return situations;
	}

    @Override
    public int checkStatus() {
    	if (this.level == this.n) return 0;
    	else return -1;
    }
  
}
