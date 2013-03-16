package game;

public class GoBoard {
	
	private static int DEFAULT_BOARD_SIZE = 19;
	
	private CellState[][] board;
	
	public GoBoard() {
		
		board = new CellState[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
		for(int i=0;i<DEFAULT_BOARD_SIZE;i++) {
			for(int j=0;j<DEFAULT_BOARD_SIZE;j++) {
				board[i][j] = CellState.EMPTY;
			}
		}
		
	}
	
	public GoBoard(int cols, int rows) {
		
		board = new CellState[cols][rows];
		for(int i=0;i<cols;i++) {
			for(int j=0;j<rows;j++) {
				board[i][j] = CellState.EMPTY;
			}
		}
		
	}
	//@Override
	public boolean equals (GoBoard otherBoard) {

		if (getCols()!=otherBoard.getCols() || getRows()!=otherBoard.getRows()) {
			return false;
		} else {
			for(int i=0;i<getCols();i++) {
				for(int j=0;j<getRows();j++) {
					if(getStone(i,j) != otherBoard.getStone(i,j)) {
						return false;
					}
				}
			}
		}
		
		return true;
		
	}
	
	public CellState getStone(int x, int y) {
		
		if (x<0 || y<0 || x>=board.length || y>=board[0].length) {
// see CellState.java
			return CellState.NOLOC;
		} else {
			return board[x][y];
		}
		
	}
	
	public CellState getStone(Coord coord) {
		
		if (coord.getX()<0 || coord.getY()<0 || coord.getX()>=board.length || coord.getY()>=board[0].length) {
			return CellState.NOLOC;
		} else {
			return board[coord.getX()][coord.getY()];
		}
		
	}
	
	public void setStone(int x, int y, CellState state) {
		
		board[x][y] = state;
		
	}
	
	public void setStone(Coord coord, CellState state) {
		
		board[coord.getX()][coord.getY()] = state;
		
	}
	
	public int getCols () {
		
		return board.length;
		
	}
	
	public int getRows () {
		
		return board[0].length;
		
	}

	public void copyBoardFrom(GoBoard otherBoard) {
		
		if (otherBoard!=null && getCols()==otherBoard.getCols() && getRows()==otherBoard.getRows()) {
			
			for(int i=0;i<getCols();i++) {
				for(int j=0;j<getRows();j++) {
	
					setStone(i,j,otherBoard.getStone(i,j));
					
				}
			}
			
		}
		
	}

}
