package jgraphs.app.tictactoe;

import java.util.ArrayList;
import java.util.List;

import jgraphs.core.situation.ISituation;

public class TicTacToeSituation implements ISituation {
    private int n;
    private int level;
	private int[][] values;

    public TicTacToeSituation() {
    	this.n = 3;
    	this.level = 0;
    	this.values = new int[n][n];
    }
    
    @Override
    public ISituation createNewSituation() {
    	var copy = new TicTacToeSituation();
    	copy.level = this.level;
        copy.values = new int[this.n][this.n];
        for (var i = 0; i < this.n; i++) {
            for (var j = 0; j < this.n; j++) {
                copy.values[i][j] = this.values[i][j];
            }
        }
        return copy;
    }
    
	@Override
	public List<ISituation> nextSituations() {
		return this.nextSituations(-1, null);
	}
    
	@Override
	public List<ISituation> nextSituations(int participant, Object value) {
		List<ISituation> situations = new ArrayList<>();
		
        for (var i = 0; i < this.values.length; i++) {
            for (var j = 0; j < this.values.length; j++) {
                if (this.values[i][j] == 0) {
					var situation = (TicTacToeSituation)this.createNewSituation();
					situation.values[i][j] = participant;
					situation.level += 1;
					situations.add(situation);
                }
            }
        }
        return situations;
	}
   
    @Override
    public int checkStatus() {
        var boardSize = values.length;
        var maxIndex = boardSize - 1;
        var diag1 = new int[boardSize];
        var diag2 = new int[boardSize];
        
        for (int i = 0; i < boardSize; i++) {
            var row = values[i];
            var col = new int[boardSize];
            for (int j = 0; j < boardSize; j++) {
                col[j] = values[j][i];
            }
            
            var checkRowForWin = checkForWin(row);
            if(checkRowForWin != -1)
                return checkRowForWin;
            
            var checkColForWin = checkForWin(col);
            if(checkColForWin != -1)
                return checkColForWin;
            
            diag1[i] = values[i][i];
            diag2[i] = values[maxIndex - i][i];
        }

        var checkDia1gForWin = checkForWin(diag1);
        if(checkDia1gForWin != -1)
            return checkDia1gForWin;
        
        var checkDiag2ForWin = checkForWin(diag2);
        if(checkDiag2ForWin != -1)
            return checkDiag2ForWin;
        
        if (this.nextSituations().size() > 0)
            return -1; //IN PROGRESS
        else 
            return 0; //DRAW
    }
	
    @Override
    public String getValuesToString() {
    	var v = new StringBuilder();
        for (var i = 0; i < this.values.length; i++) {
            for (int j = 0; j < this.values.length; j++) {
            	v.append(this.values[i][j] + " ");
            }
            v.append("\n");
        }
        return v.toString();
    }
    
    @Override
    public String getValuesToHTML() {
        var v = new StringBuilder();
        for (var i = 0; i < this.values.length; i++) {
            v.append("|");
        	for (var j = 0; j < this.values.length; j++) {
        		v.append(this.values[i][j] + "|");
        	}
        	v.append("<br/>");
        } 
        return v.toString();
    }  
  
    @Override
    public String toString() {
    	var sb = new StringBuilder();
    	sb.append("Board:\n");
    	sb.append("\tTotal moves: \t" + this.level + "\n");
    	sb.append("\tStatus: \t" + this.checkStatus() + "\n");
        sb.append("\tValues: \n" + this.getValuesToString());
        sb.append("\n");
        return sb.toString();
    }
    
    private int checkForWin(int[] row) {
        var isEqual = true;
        var size = row.length;
        int previous = getStatus(row[0]);
        for (var i = 0; i < size; i++) {
            if (previous != getStatus(row[i])) {
                isEqual = false;
                break;
            }
            previous = getStatus(row[i]);
        }
        if(isEqual)
            return previous;
        else
            return -1;
    }
    
    private int getStatus(int value) {
    	if (value == 1) return 1;
    	else if (value == 2) return 2;
    	else return -1;
    }

}
