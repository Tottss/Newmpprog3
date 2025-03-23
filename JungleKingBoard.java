import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class JungleKingBoard extends JFrame {
    private static final int ROWS = 7;
    private static final int COLS = 9;
    private JPanel boardPanel;
    private JButton[][] squares;
    private JButton selectedPiece = null;
    private ArrayList<Piece> pieces;

    public JungleKingBoard() {
        setTitle("7x9 Chess Board");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        squares = new JButton[ROWS][COLS];
        initializeBoard();
        
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            JungleKingBoard board = new JungleKingBoard();
            board.setVisible(true);
        });
    }
}
