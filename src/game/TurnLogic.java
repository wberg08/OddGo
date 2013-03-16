package game;

import util.CoordSet;

public class TurnLogic {
	
	private GoBoard board;
	
	private GoGUI gui;
	
	public GoBoard doTurn(GoBoard board, Coord newStone, CellState playerColour, GoGUI gui) {
		
		this.board = board;
		this.gui = gui;
		gui.setLastTurnWasSkipped(false);
		
		board.setStone(newStone, playerColour);
		CellState antiColour;
		
		if (playerColour.equals(CellState.WHITE)) {
			antiColour = CellState.BLACK;
		} else {
			antiColour = CellState.WHITE;
		}
		
		for (Coord c : getAdjacentCoords(newStone)) {
			
			if (board.getStone(c).equals(antiColour) && groupIsDead(c)) {
				captureGroup(c);
			}
			
		}
		
		if (groupIsDead(newStone)) {
			captureGroup(newStone);
		}
		
		if (gui.getBoardBeforeLast()!=null && board.equals(gui.getBoardBeforeLast())) {
			
			gui.setBlackScore(gui.getLastBlackScore());
			gui.setWhiteScore(gui.getLastWhiteScore());
			
			gui.setTurnNumber(gui.getTurnNumber()-1);
			
			gui.setWarningLabel("Ko rule violation");
			
			gui.unsetCrossCoord();
			gui.forceBoardViewRepaint();
			
			return gui.getLastBoard();
			
		} else {
			
			if (gui.getBoardBeforeLast()!=null) {
				
				gui.getBoardBeforeLast().copyBoardFrom(gui.getLastBoard());
				
			} else {
				
				gui.makeNewBoardBeforeLast();
				
				if (gui.getLastBoard()==null) {
					
					gui.makeNewLastBoard();
					
				}
				
			}
			
			gui.getLastBoard().copyBoardFrom(board);
			gui.setLastBlackScore(gui.getBlackScore());
			gui.setLastWhiteScore(gui.getWhiteScore());
			
			gui.setWarningLabel("");
			
			return board;
		}
		
	}
	
	private boolean groupIsDead (Coord coord) {
		
		CoordSet checkedCoords = new CoordSet();
		checkedCoords.add(coord);
		
		return (
			!anyAdjacentIsEmpty(coord) &&
			groupIsDeadPrime(new Coord(coord.getX(),coord.getY()-1),board.getStone(coord),checkedCoords) &&
			groupIsDeadPrime(new Coord(coord.getX()+1,coord.getY()),board.getStone(coord),checkedCoords) &&
			groupIsDeadPrime(new Coord(coord.getX(),coord.getY()+1),board.getStone(coord),checkedCoords) &&
			groupIsDeadPrime(new Coord(coord.getX()-1,coord.getY()),board.getStone(coord),checkedCoords)
		);
		
	}
	
	private boolean groupIsDeadPrime (Coord coord, CellState colour, CoordSet checkedCoords) {
		
		checkedCoords.add(coord);
		
		if(!(board.getStone(coord).equals(CellState.EMPTY) ||
		   board.getStone(coord).equals(colour))) {
			
			return true;
			
		}
		
		if(anyAdjacentIsEmpty(coord)) {
			
			return false;
		
		} else {
			
			for (Coord c : getAdjacentCoords(coord)) {
				if(!checkedCoords.contains(c) && !groupIsDeadPrime(c,colour,checkedCoords)) {
					return false;
				}
			}
			
		}
		
		return true;
		
	}
	
	private void captureGroup(Coord coord) {
		
		captureGroupPrime(coord,board.getStone(coord));
		
	}
	
	private void captureGroupPrime(Coord coord, CellState colour) {
		
		if (board.getStone(coord).equals(colour)) {
			board.setStone(coord, CellState.EMPTY);
			gui.addToCurrent(1);
			for (Coord c : getAdjacentCoords(coord)) {
				captureGroupPrime(c, colour);
			}
		}
		
	}
	
	private boolean anyAdjacentIsEmpty (Coord coord) {
		
		for (Coord a : getAdjacentCoords(coord)) {
			
			if(board.getStone(a) == CellState.EMPTY)
				return true;
			
		}
		
		return false;
		
	}
	
	private Coord[] getAdjacentCoords (Coord coord) {
		
		return new Coord[] {
			new Coord(coord.getX(),coord.getY()-1),
			new Coord(coord.getX()+1,coord.getY()),
			new Coord(coord.getX(),coord.getY()+1),
			new Coord(coord.getX()-1,coord.getY()),
		};
		
	}
	
}
