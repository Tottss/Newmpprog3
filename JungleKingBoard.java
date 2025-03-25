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
    private ArrayList<Piece> pieces;

    public JungleKingBoard() {
        // setTitle("7x9 Chess Board");
        // setSize(600, 500);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        squares = new JButton[ROWS][COLS];
		pieces = new ArrayList<>();
        initializeBoard();
		setLake();
		setBases();
		
        instantiatePieces();
		setPieces();
		
        add(boardPanel, BorderLayout.CENTER);
    }

    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                squares[row][col] = new JButton();
                
                // Set alternating colors for a chessboard pattern
                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(Color.LIGHT_GRAY);
                } else {
                    squares[row][col].setBackground(Color.DARK_GRAY);
                }
                
                squares[row][col].addActionListener(new PieceMoveListener(row, col));
                
                // TODO: Implement placing pieces on the board here
                
                boardPanel.add(squares[row][col]);
            }
        }
    }

    private class PieceMoveListener implements ActionListener {
        private int row, col;

        public PieceMoveListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = squares[row][col];
            if (selectedPiece == null && !clickedButton.getText().isEmpty()) {
                // Select a piece
                selectedPiece = clickedButton;
                selectedPiece.setBackground(Color.YELLOW);
            } else if (selectedPiece != null && clickedButton != selectedPiece) {
                // Move the piece
                clickedButton.setText(selectedPiece.getText());
                selectedPiece.setText("");
                selectedPiece.setBackground((getButtonIndex(selectedPiece) % 2 == 0) ? Color.LIGHT_GRAY : Color.DARK_GRAY);
                selectedPiece = null;
            }
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
	
	public void setLake () { // sets the lake to be '~'
		int i, j;
		
		for (i = 1; i < 3; i++) {
			for (j = 3; j < 6; j++) {
				squares[i][j].setIcon(loadPieceImage("lake"));
			}
		}
		
		for (i = 4; i < 6; i++) {
			for (j = 3; j < 6; j++) {
				squares[i][j].setIcon(loadPieceImage("lake"));
			}
		}
	}
	
	public void setBases () {
		// left base
		squares[2][0].setIcon(loadPieceImage("trap"));
		squares[3][0].setIcon(loadPieceImage("den-green"));
		squares[3][1].setIcon(loadPieceImage("trap"));
		squares[4][0].setIcon(loadPieceImage("trap"));
		
		// right base;
		squares[2][8].setIcon(loadPieceImage("trap"));
		squares[3][8].setIcon(loadPieceImage("den-blue"));
		squares[3][7].setIcon(loadPieceImage("trap"));
		squares[4][8].setIcon(loadPieceImage("trap"));
	}
    
    public void instantiatePieces () { // create pieces for player 1 and 2
		int i, j;
		
		String[] pieceNames = {"R", "C", "D", "W", "LD", "T", "LN", "E"};
		int[] strengths = {1, 2, 3, 4, 5, 6, 7, 8};
		
		for (i = 1; i <= 2; i++) { // twice for both players
			for (j = 0; j < strengths.length; j++) {
				pieces.add(new Piece(pieceNames[j] + i, strengths[j], i));
			}
		}
	}
	
	public void setPieces () {
		int i;
		
		String[] pieceNames = {"rat", "cat", "dog", "wolf", "leopard", "tiger", "lion", "elephant"};
		
		int[][] positionsP1 = {{6, 2}, {1, 1}, {5, 1}, {2, 2}, {4, 2}, {0, 0}, {6, 0}, {0, 2}};
		int[][] positionsP2 = {{0, 6}, {5, 7}, {1, 7}, {4, 6}, {2, 6}, {6, 8}, {0, 8}, {6, 6}};
		
		for (i = 0; i < positionsP1.length; i++) {
			squares[positionsP1[i][0]][positionsP1[i][1]].setIcon(loadPieceImage(pieceNames[i] + "-blue"));
			squares[positionsP2[i][0]][positionsP2[i][1]].setIcon(loadPieceImage(pieceNames[i] + "-green"));
		}
	}
	
	private ImageIcon loadPieceImage (String fileName) {
		String path = "img/" + fileName + ".png";
		return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	}
	
    // public static void main(String[] args) {
        // SwingUtilities.invokeLater(() -> {
            
            // JungleKingBoard board = new JungleKingBoard();
            // board.setVisible(true);
        // });
    // }
}
