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
	private board board;
    private ArrayList<Piece> pieces;

    public JungleKingBoard() {
        // setTitle("7x9 Chess Board");
        // setSize(600, 500);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        squares = new JButton[ROWS][COLS];
		pieces = new ArrayList<>();
		board = new board();
		
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
		squares[3][0].setIcon(loadPieceImage("den-green"));
		squares[3][8].setIcon(loadPieceImage("den-blue"));
    }
	
	private ImageIcon loadPieceImage (String fileName) {
		String path = "img/" + fileName + ".png";
		return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	}

    private class PieceMoveListener implements ActionListener {
        private int row, col, selectedPieceR, selectedPieceC;

        public PieceMoveListener(int row, int col) { // initializes clicked row and column
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = squares[row][col];
			Piece piece;
			
            if (selectedPiece == null && clickedButton.getIcon() != null) { // if there's no selected piece b4
				
				if (!(board.getGrid(row, col) instanceof Piece)) // if tile chosen is not a piece
					return;
				
                // select a piece
                selectedPiece = clickedButton;
                selectedPiece.setBackground(Color.YELLOW);
				selectedPieceR = row;
				selectedPieceC = col;
                System.err.println("WORKS");
            }
			else if (selectedPiece != null && clickedButton != selectedPiece) { // attemping to move the piece
				
				if (board == null) // board must be initialized
					return;
                else {
                    System.err.println("board is not null");
                }
				if (!(board.getGrid(selectedPieceR, selectedPieceC) instanceof Piece)) { // if not a piece
					resetSelection();
                    System.err.println("WORKS1");
					return;
				}
				else {
                    System.err.println("is a piece");
                }
				// if is a piece
				piece = (Piece)board.getGrid(selectedPieceR, selectedPieceC);
				
				if (piece != null && board.isValidMove(piece, row, col)) { // move the piece
					
					if (board.movePiece(piece, row, col)) {
						clickedButton.setIcon(selectedPiece.getIcon());
						selectedPiece.setIcon(null);
						clickedButton.revalidate();
						clickedButton.repaint();
                        System.err.println("WORKS2");
					}
				}
                else if (!(board.isValidMove(piece, row, col))){
                    System.err.println("isvalid move is problem");
                }
                resetSelection();
            }
        }
		
		private Color getOriginalColor (int row, int col) {
			if ((row + col) % 2 == 0)
				return Color.LIGHT_GRAY;
			return Color.DARK_GRAY;
		}
		
		private void resetSelection () {
			if (selectedPiece != null)
				selectedPiece.setBackground(getOriginalColor(row, col));
			
			selectedPiece = null;
			selectedPieceR = -1;
			selectedPieceC = -1;
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
