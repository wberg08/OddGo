package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class GoGUI implements ActionListener {
	
	private GoBoard board;
	private BoardView boardView; 

	private int boardRows;
	private int boardCols;
	private ExitAction exitAction;
	private JFrame topFrame;
	private JLabel turnLabel;
	private JLabel playLabel;
	private JButton skipButton;
	private JButton undoButton;
	private JButton scoreButton;
	private JButton aboutButton;
	private JLabel warningLabel;
	private int turnNumber = 0;
	private TurnLogic turnLogic;
	private int whiteScore;
	private int blackScore;
	private boolean lastTurnWasSkipped;
	private boolean gameOver = false;
	
	private GoBoard lastBoard;
	private int lastBlackScore;
	private int lastWhiteScore;
	
	private GoBoard boardBeforeLast;
    
	public void makeGUI () {
		
		topFrame = new JFrame();
		exitAction = new ExitAction(topFrame, this);
		topFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		topFrame.addWindowListener(exitAction);
		
		String s = (String)JOptionPane.showInputDialog(
		                    topFrame,
		                    "Columns across? Leave blank for 19x19.",
		                    "Board size",
		                    JOptionPane.CLOSED_OPTION
		);
		
		if (s!=null && !s.equals("")){
			
			boardCols = Integer.parseInt(s);
			
			String r = (String) JOptionPane.showInputDialog(
                    topFrame,
                    "Rows down? Leave blank for 19.",
                    "Board size",
                    JOptionPane.CLOSED_OPTION
			);

			if (r!=null && !r.equals("")) {
				boardRows=Integer.parseInt(r);
			} else {
				boardRows=19;
			}
			
		} else {
			boardCols = 19;
			boardRows = 19;
		}
		
		board = new GoBoard(boardCols,boardRows);
		
		JPanel masterPanel = new JPanel(new BorderLayout());
		
// set turn logic
		turnLogic = new TurnLogic();
		
		boardView = new BoardView(this,board);
		
		turnLabel = new JLabel("Turn: 0 Black: 0 White: 0",SwingConstants.CENTER);
		playLabel = new JLabel("White to play",SwingConstants.CENTER);
		skipButton = new JButton("Skip turn");
		undoButton = new JButton("Undo last turn");
		scoreButton = new JButton("Score");
		undoButton.setEnabled(false);
		scoreButton.setEnabled(false);
		aboutButton = new JButton("About");
		
		warningLabel = new JLabel("",JLabel.CENTER);
		warningLabel.setForeground(Color.RED);
		
		JPanel topPanel = new JPanel(new GridLayout());
		topPanel.add(turnLabel);
		topPanel.add(skipButton);
		topPanel.add(undoButton);
		topPanel.add(warningLabel);
		
		JPanel bottomPanel = new JPanel(new GridLayout());
		bottomPanel.add(scoreButton);
		bottomPanel.add(playLabel);
		bottomPanel.add(aboutButton);
		
		skipButton.setActionCommand("skip");
		undoButton.setActionCommand("undo");
		scoreButton.setActionCommand("score");
		aboutButton.setActionCommand("about");
		skipButton.addActionListener(this);
		undoButton.addActionListener(this);
		scoreButton.addActionListener(this);
		aboutButton.addActionListener(this);
		
		masterPanel.add(boardView,BorderLayout.CENTER);
		masterPanel.add(topPanel,BorderLayout.NORTH);
		masterPanel.add(bottomPanel,BorderLayout.SOUTH);
		topFrame.setContentPane(masterPanel);
		topFrame.setTitle("OddGo from IC Go Club");
		topFrame.pack();
		topFrame.setVisible(true);
		
	}
	
	public boolean canClose() {
		
		boolean stonesPresent = false;
		
		OUTER_LOOP:
		for(int i=0;i<board.getCols();i++) {
			for(int j=0;j<board.getRows();j++) {
				if(!board.getStone(i, j).equals(CellState.EMPTY)) {
					stonesPresent = true;
					break OUTER_LOOP;
				}
			}
		}
		
		if(stonesPresent) {
			
			int arg = JOptionPane.showConfirmDialog(
                    topFrame,
                    "Really quit?",
                    "OddGo",
                    JOptionPane.YES_NO_OPTION
			);
			
			return arg==JOptionPane.YES_OPTION;
			
		}
		
		return true;
		
	}
	
	public void setTurnLabel (String label) {
		
		turnLabel.setText(label);
		turnLabel.repaint();
		
	}
	
	public void setPlayLabel (String label) {
		
		playLabel.setText(label);
		playLabel.repaint();
		
	}
	
	public void setWarningLabel (String label) {
		
		warningLabel.setText(label);
		warningLabel.repaint();
		
	}

	public void setTurnNumber(int turnNumber) {
		
		this.turnNumber = turnNumber;
		updateTurnLabel();
		
	}

	public int getTurnNumber() {
		return turnNumber;
	}
	
	public boolean isBlacksTurn() {

		return !(turnNumber%2==0);
		
	}
	
	public TurnLogic getTurnLogic() {
		
		return turnLogic;
		
	}

	public void setWhiteScore(int whiteScore) {
		this.whiteScore = whiteScore;
		updateTurnLabel();
		
	}

	public int getWhiteScore() {
		return whiteScore;
	}

	public void setBlackScore(int blackScore) {
		this.blackScore = blackScore;
		updateTurnLabel();
	}

	public int getBlackScore() {
		return blackScore;
	}
	
	public void addToCurrent(int points) {
		if(isBlacksTurn()) {
			blackScore += points;
		} else {
			whiteScore += points;
		}

		updateTurnLabel();
		
	}
	
	public void nextTurn() {
		
		setTurnNumber(getTurnNumber() + 1);
		updateTurnLabel();
		
	}
	
	public void previousTurn() {
		
		setTurnNumber(getTurnNumber() - 1);
		updateTurnLabel();
		
	}
	
	public void updateTurnLabel() {

		setTurnLabel(	"Turn: " + Integer.toString(getTurnNumber()) + " " +
						"Black: " + Integer.toString(getBlackScore()) + " " +
						"White: " + Integer.toString(getWhiteScore())
		);
		
	}
	private void endGame() {

		gameOver = true;
		setPlayLabel("Scoring");
		skipButton.setEnabled(false);
		undoButton.setEnabled(false);
		scoreButton.setEnabled(true);
		
	}

	public void setLastTurnWasSkipped (boolean b) {
		
		lastTurnWasSkipped = b;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("skip")) {
			
			nextTurn();
			
			if(lastTurnWasSkipped) {
				
				endGame();
				
			} else {
				
				setLastTurnWasSkipped(true);
			
			}
			
		} else if (e.getActionCommand().equals("undo")) {
			
			if (turnNumber > 0) {
				
				board.copyBoardFrom(boardBeforeLast);
				whiteScore = lastWhiteScore;
				blackScore = lastBlackScore;
				previousTurn();
				
				setUndoButtonEnabled(false);
				
				forceBoardViewRepaint();
				
			}
			
		} else if (e.getActionCommand().equals("score")) {
			
			String winningMessage;
			
			if (getBlackScore() >= getWhiteScore()) {
				winningMessage = "Black wins!";
			} else {
				winningMessage = "White wins!";
			}
			
			JOptionPane.showMessageDialog(topFrame,
					"Black: " + getBlackScore() +
					"\nWhite: " + getWhiteScore() +
					"\n" + winningMessage,
					"Final Score",
					JOptionPane.PLAIN_MESSAGE);
			
		} else if (e.getActionCommand().equals("about")) {
			
			JOptionPane.showMessageDialog(topFrame,
					"By William Berg @ william.berg08@imperial.ac.uk" +
					"\nDistributed under GPL v3",
					"Version 1.0",
					JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		topFrame.repaint();
		
	}
	
	public boolean gameIsOver() {
		
		return gameOver;
		
	}
	
	public GoBoard getBoard () {
		
		return board;
		
	}
	
	public GoBoard getLastBoard() {
		
		return lastBoard;
		
	}
	
	public GoBoard getBoardBeforeLast() {
		
		return boardBeforeLast;
		
	}
	
	public int getLastBlackScore() {
		
		return lastBlackScore;
		
	}

	public int getLastWhiteScore() {
		return lastWhiteScore;
	}

	public void setLastWhiteScore(int lastWhiteScore) {
		this.lastWhiteScore = lastWhiteScore;
	}

	public void setLastBoard(GoBoard lastBoard) {
		this.lastBoard = lastBoard;
	}

	public void setLastBlackScore(int lastBlackScore) {
		this.lastBlackScore = lastBlackScore;
	}

	public void setBoardBeforeLast(GoBoard boardBeforeLast) {
		this.boardBeforeLast = boardBeforeLast;
	}
	
	public void makeNewLastBoard() {
		
		lastBoard = new GoBoard();
		
	}
	
	public void makeNewBoardBeforeLast() {
		
		boardBeforeLast = new GoBoard();
		
	}
	
	public void enableCountButton () {
		scoreButton.setEnabled(true);
	}
	
	public void setUndoButtonEnabled(boolean set) {
		undoButton.setEnabled(set);
	}
	
	public void unsetCrossCoord() {
		boardView.unsetCrossCoord();
	}
	
	public void forceBoardViewRepaint() {
		boardView.repaint();
	}
	
}
