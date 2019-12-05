package jgraphs.game.tictactoe;

import java.util.ArrayList;
import java.util.List;

import jgraphs.core.situation.ISituation;
import jgraphs.core.situation.Position;

public class TicTacToeBoard implements ISituation {
    private int[][] situationValues;
    private int totalMoves;
    private int n;

    public TicTacToeBoard() {
    	this.n = 3;
    	this.totalMoves = 0;
    	this.situationValues = new int[n][n];
    }
    
    @Override
    public ISituation createNewSituation() {
    	var copy = new TicTacToeBoard();
    	copy.totalMoves = this.getTotalMovements();
        copy.situationValues = new int[this.n][this.n];
        for (var i = 0; i < this.n; i++) {
            for (var j = 0; j < this.n; j++) {
                copy.situationValues[i][j] = this.getSituationValues()[i][j];
            }
        }
        return copy;
    }

	@Override
	public int getTotalMovements() {
		return this.totalMoves;
	}
    
    @Override
    public int[][] getSituationValues() {
        return this.situationValues;
    }
    
    @Override
    public String getSituationValuesToHTML() {
        var values = new StringBuilder();
        for (var i = 0; i < situationValues.length; i++) {
            values.append("|");
        	for (var j = 0; j < situationValues.length; j++) {
        		values.append(situationValues[i][j] + "|");
        	}
        	values.append("<br/>");
        } 
        return values.toString();
    }
    
    @Override
    public void setSituationValues(int[][] situationValues) {
        this.situationValues = situationValues;
    }    
	
    @Override
    public List<Position> getEmptyPositions() {
        int size = this.situationValues.length; 
        List<Position> emptyPositions = new ArrayList<>();
        for (var i = 0; i < size; i++) {
            for (var j = 0; j < size; j++) {
                if (situationValues[i][j] == 0)
                    emptyPositions.add(new Position(i, j));
            }
        }
        return emptyPositions;
    }
    
    @Override
    public void performMovement(int player, Position p) {
        this.totalMoves++;
        this.situationValues[p.x][p.y] = player;
    }

    @Override
    public int checkStatus() {
        var boardSize = situationValues.length;
        var maxIndex = boardSize - 1;
        var diag1 = new int[boardSize];
        var diag2 = new int[boardSize];
        
        for (int i = 0; i < boardSize; i++) {
            var row = situationValues[i];
            var col = new int[boardSize];
            for (int j = 0; j < boardSize; j++) {
                col[j] = situationValues[j][i];
            }
            
            var checkRowForWin = checkForWin(row);
            if(checkRowForWin != -1)
                return checkRowForWin;
            
            var checkColForWin = checkForWin(col);
            if(checkColForWin != -1)
                return checkColForWin;
            
            diag1[i] = situationValues[i][i];
            diag2[i] = situationValues[maxIndex - i][i];
        }

        var checkDia1gForWin = checkForWin(diag1);
        if(checkDia1gForWin != -1)
            return checkDia1gForWin;
        
        var checkDiag2ForWin = checkForWin(diag2);
        if(checkDiag2ForWin != -1)
            return checkDiag2ForWin;
        
        if (getEmptyPositions().size() > 0)
            return -1; //IN PROGRESS
        else 
            return 0; //DRAW
    }
  
    @Override
    public String toString() {
    	var sb = new StringBuilder();
    	sb.append("Board:\n");
    	sb.append("\tTotal moves: \t" + this.totalMoves + "\n");
    	sb.append("\tGame status: \t" + this.checkStatus() + "\n");
        var size = this.situationValues.length;
        for (var i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            	sb.append(situationValues[i][j] + " ");
            }
            sb.append("\n");
        }
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
