package alphastar.game.tictactoe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alphastar.core.Position;
import alphastar.core.structure.EGameStatus;
import alphastar.core.structure.EPlayer;
import alphastar.core.structure.IBoard;
import alphastar.core.structure.IPosition;
import alphastar.core.structure.IState;

public class Board implements IBoard {
	private static Logger log = LoggerFactory.getLogger(Board.class);
	IState state;
    int[][] boardValues;
    int totalMoves;
	int n;

    public Board(IState state) {
    	this.state = state;
    	try (InputStream input = new FileInputStream("src/main/java/config.properties")) {
            var prop = new Properties();
            prop.load(input);
            n = Integer.parseInt(prop.getProperty("board.default_size"));
    	} catch (IOException ex) {
       		log.error(ex.getMessage());
    	}
    	boardValues = new int[n][n];
    }

    public Board(IState state, IBoard board) {
    	this.state = state;
    	this.totalMoves = board.getTotalMoves();
        this.boardValues = new int[board.getBoardValues().length][board.getBoardValues().length];
        this.n = boardValues.length;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                this.boardValues[i][j] = board.getBoardValues()[i][j];
            }
        }
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
        for (int i = 0; i < boardValues.length; i++) {
            values.append("|");
        	for (int j = 0; j < boardValues.length; j++) {
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
	public IState getState() {
		return this.state;
	}
	
    @Override
    public List<IPosition> getEmptyPositions() {
        int size = this.boardValues.length;
        List<IPosition> emptyPositions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boardValues[i][j] == 0)
                    emptyPositions.add(new Position(i, j));
            }
        }
        return emptyPositions;
    }
    
    @Override
    public void performMove(EPlayer player, IPosition p) {
        this.totalMoves++;
        boardValues[p.getX()][p.getY()] = player.ordinal();
    }

    @Override
    public EGameStatus checkStatus() {
        int boardSize = boardValues.length;
        int maxIndex = boardSize - 1;
        int[] diag1 = new int[boardSize];
        int[] diag2 = new int[boardSize];
        
        for (int i = 0; i < boardSize; i++) {
            int[] row = boardValues[i];
            int[] col = new int[boardSize];
            for (int j = 0; j < boardSize; j++) {
                col[j] = boardValues[j][i];
            }
            
            var checkRowForWin = checkForWin(row);
            if(checkRowForWin != null)
                return checkRowForWin;
            
            var checkColForWin = checkForWin(col);
            if(checkColForWin != null)
                return checkColForWin;
            
            diag1[i] = boardValues[i][i];
            diag2[i] = boardValues[maxIndex - i][i];
        }

        var checkDia1gForWin = checkForWin(diag1);
        if(checkDia1gForWin != null)
            return checkDia1gForWin;
        
        var checkDiag2ForWin = checkForWin(diag2);
        if(checkDiag2ForWin != null)
            return checkDiag2ForWin;
        
        if (getEmptyPositions().size() > 0)
            return EGameStatus.In_Progress;
        else 
            return EGameStatus.Draw;
    }
  
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Board:\n");
    	sb.append("\tTotal moves: \t" + this.totalMoves + "\n");
    	sb.append("\tGame status: \t" + this.checkStatus() + "\n");
        var size = this.boardValues.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            	sb.append(boardValues[i][j] + " ");
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
    
    private EGameStatus checkForWin(int[] row) {
        boolean isEqual = true;
        int size = row.length;
        EGameStatus previous = getStatus(row[0]);
        for (int i = 0; i < size; i++) {
            if (previous != getStatus(row[i])) {
                isEqual = false;
                break;
            }
            previous = getStatus(row[i]);
        }
        if(isEqual)
            return previous;
        else
            return null;
    }
    
    private EGameStatus getStatus(int value) {
    	if (value == 1) return EGameStatus.P1Won;
    	else if (value == 2) return EGameStatus.P2Won;
    	else return EGameStatus.In_Progress;
    }

}
