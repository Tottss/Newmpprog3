import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class JungleKingBoard extends JPanel {
    private static final int ROWS = 7;
    private static final int COLS = 9;
    private JPanel boardPanel;
    private JButton[][] squares;
    private JButton selectedPiece = null;
	private Board board;
    private ArrayList<Piece> pieces;

    public JungleKingBoard() {
        // setTitle("7x9 Chess Board");
        // setSize(600, 500);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        squares = new JButton[ROWS][COLS];
		pieces = new ArrayList<>();
		board = new Board();
		
        initializeBoard();
		
        add(boardPanel, BorderLayout.CENTER);
    }

    private void initializeBoard() {
		int row, col;
		Piece piece;
		String imageName;
		
        for (row = 0; row < ROWS; row++) { // set up board background
            for (col = 0; col < COLS; col++) {
                squares[row][col] = new JButton();
				
				if ((row + col) % 2 == 0)
					squares[row][col].setBackground(Color.LIGHT_GRAY);
				
				else
					squares[row][col].setBackground(Color.DARK_GRAY);
					
                
                squares[row][col].addActionListener(new PieceMoveListener(row, col));
                
                boardPanel.add(squares[row][col]);
            }
        }
		
		for (row = 0; row < ROWS; row++) { // set up terrain and pieces
            for (col = 0; col < COLS; col++) {
				
				if (board.getGrid(row, col) instanceof Character) { // if is character
					
					if ((char)board.getGrid(row, col) == '~')
						squares[row][col].setIcon(loadPieceImage("lake"));
					
					else if ((char)board.getGrid(row, col) == '#')
						squares[row][col].setIcon(loadPieceImage("trap"));
				}
				
				else if (board.getGrid(row, col) instanceof Piece) { // if is piece
					piece = (Piece)board.getGrid(row, col);
					imageName = piece.getPieceName();
					squares[row][col].setIcon(loadPieceImage(imageName));
				}
            }
        }
		
		// manually setup dens
		squares[3][0].setIcon(loadPieceImage("den-blue"));
		squares[3][8].setIcon(loadPieceImage("den-green"));
    }
	
	private ImageIcon loadPieceImage (String fileName) {
		String path = "img/" + fileName + ".png";
		return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	}
	
	private class PieceMoveListener implements ActionListener {
        private int row, col, selectedPieceR = -1, selectedPieceC = -1;

        public PieceMoveListener(int row, int col) { // initializes clicked row and column
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
			if (selectedPiece == null) {
				// Select piece if it belongs to current player
				Object gridObj = board.getGrid(row, col);
				if (gridObj instanceof Piece) {
					selectedPiece = squares[row][col];
					selectedPiece.setBackground(Color.YELLOW); // Highlight selected
				}
			} 
			
			else {
				// Attempt to move
				JButton target = (JButton) e.getSource();
				int newRow = row;
				int newCol = col;
				
				// Get the selected piece from the board
				Piece piece = (Piece) board.getGrid(
					selectedPiece.getY() / selectedPiece.getHeight(),
					selectedPiece.getX() / selectedPiece.getWidth()
				);
				
				if (piece != null && board.movePiece(piece, newRow, newCol)) {
					// Update GUI
					squares[newRow][newCol].setIcon(selectedPiece.getIcon());
					squares[newRow][newCol].setBackground(target.getBackground());
					selectedPiece.setIcon(null);
					selectedPiece.setBackground((selectedPiece.getY() + selectedPiece.getX()) % 2 == 0 
						? Color.LIGHT_GRAY : Color.DARK_GRAY);
					updateBoardDisplay();
				}
				selectedPiece = null; // Reset selection
			}
		}
		
		private Color getOriginalColor (int row, int col) {
			if ((row + col) % 2 == 0)
				return Color.LIGHT_GRAY;
			return Color.DARK_GRAY;
		}
		
		private void resetSelection () {
			if (selectedPiece != null)
				selectedPiece.setBackground(getOriginalColor(selectedPieceR, selectedPieceC));
			
			selectedPiece = null;
			
			if (selectedPieceR >= 0 && selectedPieceC >= 0) {
				selectedPieceR = -1;
				selectedPieceC = -1;
			}
		}
		
		private void updateBoardDisplay () {
			Piece p;
			char terrain;
			
			for (int r = 0; r < ROWS; r++) {
				for (int c = 0; c < COLS; c++) {
					squares[r][c].setIcon(null); // clear current icon
					
					
					if (board.getGrid(r, c) instanceof Character) { // set terrain
						terrain = (char)board.getGrid(r, c);
						if (terrain == '~')
							squares[r][c].setIcon(loadPieceImage("lake"));
						else if (terrain == '#')
							squares[r][c].setIcon(loadPieceImage("trap"));
					}
					
					else if (board.getGrid(r, c) instanceof Piece) { // set pieces
						p = (Piece)board.getGrid(r, c);
						squares[r][c].setIcon(loadPieceImage(p.getPieceName()));
					}
				}
			}
			
			// reset dens in case modified
			squares[3][0].setIcon(loadPieceImage("den-blue"));
			squares[3][8].setIcon(loadPieceImage("den-green"));
		}
    }

    private int getButtonIndex(JButton button) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (squares[row][col] == button) {
                    return row + col;
                }
            }
        }
        return -1;
    }
}
