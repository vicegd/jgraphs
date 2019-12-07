package jgraphs.app.permutation;

import java.util.ArrayList;
import java.util.List;

import jgraphs.core.situation.ISituation;
import jgraphs.core.situation.Position;

public class PermutationSituation implements ISituation {
	private int[] values;
	private boolean[] used;
	private int totalMoves;
	private int n;

    public PermutationSituation() {
    	this.totalMoves = 0;
    }
    
    public void setSize(int n) {
    	this.n = n;
    	this.values = new int[n];
    	this.used = new boolean[n];
    }
    
    @Override
    public ISituation createNewSituation() {
    	var copy = new PermutationSituation();
    	copy.totalMoves = this.totalMoves;
    	copy.n = this.n;
        copy.values = new int[this.n];
        for (var i = 0; i < this.n; i++) {
            copy.values[i] = this.values[i];
        }
        copy.used = new boolean[this.n];
        for (var i = 0; i < this.n; i++) {
            copy.used[i] = this.used[i];
        }
        return copy;
    }

	@Override
	public int getTotalMovements() {
		return this.totalMoves;
	}
    
    @Override
    public int[][] getSituationValues() {
        return null;
    }
    
    @Override
    public String getSituationValuesToHTML() {
        var v = new StringBuilder();
        for (var i = 0; i < used.length; i++) {
            v.append(values[i]+1 + " ");
        } 
        return v.toString();
    }
    
    @Override
    public void setSituationValues(int[][] situationValues) {
        //this.situationValues = situationValues;
    }    
	
    @Override
    public List<Position> getEmptyPositions() {
        return null;
    }
    
	@Override
	public List<ISituation> getNextSituations() {
		List<ISituation> situations = new ArrayList<>();
		
		if (this.totalMoves < this.n) {			
			for (var i = 0; i < this.n; i++) {
				if (!this.used[i]) {
					var situation = (PermutationSituation)createNewSituation();
					
					situation.values[this.totalMoves] = i;
					situation.used[i] = true;
					situation.totalMoves += 1;
					situations.add(situation);
				}
			}
		}
		return situations;
	}

    
    @Override
    public void performMovement(int participant, Position p) {
    }

    @Override
    public int checkStatus() {
    	if (this.totalMoves == this.n) return 0;
    	else return -1;
    }
  
    @Override
    public String toString() {
    	var sb = new StringBuilder();
    	sb.append("Permutations:\n");
    	sb.append("\tTotal moves: \t" + this.totalMoves + "\n");
    	sb.append("\tGame status: \t" + this.checkStatus() + "\n");
        var size = this.used.length;
        for (var i = 0; i < size; i++) {
            sb.append(values[i] + " ");
        }
        sb.append("\n");
        return sb.toString();
    }
}
