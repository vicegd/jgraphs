package jgraphs.app.permutation;

import java.util.ArrayList;
import java.util.List;

import jgraphs.core.situation.ISituation;

public class PermutationSituation implements ISituation {
	private int n;
	private int level;
	private int[] values;
	private boolean[] used;

    public PermutationSituation() {
    	this.n = 10;
    	this.level = 0;
    	this.values = new int[this.n];
    	this.used = new boolean[this.n];
    }
    
    public void setSize(int n) {
    	this.n = n;
    	this.values = new int[n];
    	this.used = new boolean[n];
    }
    
    @Override
    public ISituation createNewSituation() {
    	var copy = new PermutationSituation();
    	copy.n = this.n;
    	copy.level = this.level;
        copy.values = this.values.clone();
        copy.used = this.used.clone();
        return copy;
    }
    
	@Override
	public List<ISituation> nextSituations() {
		return this.nextSituations(0, null);
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
    
    @Override
    public String getValuesToString() {
        var v = new StringBuilder();
        for (var i = 0; i < this.used.length; i++) {
            v.append(this.values[i]+1 + " ");
        } 
        return v.toString();
    }
    
    @Override
    public String getValuesToHTML() {
        var v = new StringBuilder();
        v.append("<p>");
        for (var i = 0; i < used.length; i++) {
            v.append(values[i]+1 + " ");
        } 
        v.append("</p>");
        return v.toString();
    }
  
    @Override
    public String toString() {
    	var sb = new StringBuilder();
    	sb.append("Permutation:\n");
    	sb.append("\tTotal moves: \t" + this.level + "\n");
    	sb.append("\tStatus: \t" + this.checkStatus() + "\n");
        sb.append("\tValues: \t" + this.getValuesToString());
        sb.append("\n");
        return sb.toString();
    }
}
