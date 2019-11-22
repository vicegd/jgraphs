package jgraphs.game.tictactoe;

import java.util.ArrayList;
import java.util.List;

import jgraphs.core.board.IBoard;
import jgraphs.core.board.Position;

public class TicTacToeBoard implements IBoard {
    private int[][] boardValues;
    private int totalMoves;
    private int n;

    public TicTacToeBoard() {
    	this.n = 3;
    	this.totalMoves = 0;
    	this.boardValues = new int[n][n];
    }
    
    @Override
    public IBoard createNewBoard() {
    	var copy = new TicTacToeBoard();
    	copy.totalMoves = this.getTotalMoves();
        copy.boardValues = new int[this.n][this.n];
        for (var i = 0; i < this.n; i++) {
            for (var j = 0; j < this.n; j++) {
                copy.boardValues[i][j] = this.getBoardValues()[i][j];
            }
        }
        return copy;
    }

	@Override
	public int getTotalMoves() {
		return this.totalMoves;
	}
    
    @Override
    public int[][] getBoardValues() {
        return this.boardValues;
    }
    
    @Override
    public String getBoardValuesToHTML() {
        var values = new StringBuilder();
        for (var i = 0; i < boardValues.length; i++) {
            values.append("|");
        	for (var j = 0; j < boardValues.length; j++) {
        		values.append(boardValues[i][j] + "|");
        	}
        	values.append("<br/>");
        } 
        return values.toString();
    }
    
    @Override
    public void setBoardValues(int[][] boardValues) {
        this.boardValues = boardValues;
    }    
	
    @Override
    public List<Position> getEmptyPositions() {
        int size = this.boardValues.length; 
        List<Position> emptyPositions = new ArrayList<>();
        for (var i = 0; i < size; i++) {
            for (var j = 0; j < size; j++) {
                if (boardValues[i][j] == 0)
                    emptyPositions.add(new Position(i, j));
            }
        }
        return emptyPositions;
    }
    
    @Override
    public void performMove(int player, Position p) {
        this.totalMoves++;
        this.boardValues[p.x][p.y] = player;
    }

    @Override
    public int checkStatus() {
        var boardSize = boardValues.length;
        var maxIndex = boardSize - 1;
        var diag1 = new int[boardSize];
        var diag2 = new int[boardSize];
        
        for (int i = 0; i < boardSize; i++) {
            var row = boardValues[i];
            var col = new int[boardSize];
            for (int j = 0; j < boardSize; j++) {
                col[j] = boardValues[j][i];
            }
            
            var checkRowForWin = checkForWin(row);
            if(checkRowForWin != -1)
                return checkRowForWin;
            
            var checkColForWin = checkForWin(col);
            if(checkColForWin != -1)
                return checkColForWin;
            
            diag1[i] = boardValues[i][i];
            diag2[i] = boardValues[maxIndex - i][i];
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
        var size = this.boardValues.length;
        for (var i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            	sb.append(boardValues[i][j] + " ");
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
