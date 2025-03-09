package mpprog3;
import java.util.*;

public class board { // initialize board and pieces
	private grid[][] board;
	private ArrayList<Piece> pieces;

	public board () {
		board = new grid[7][9]; // initializes the 7x9 grid
		pieces = new ArrayList<>();
		
		setOnlyBoard();
		setLake();
		setBases();
		instantiatePieces();
		setPieces();
	}
	
	public void setOnlyBoard () { // initializes board to be '.' only
		int i, j;
		
		for (i = 0; i < board.length; i++) {
			for (j = 0; j < board[i].length; j++) {
				board[i][j] = new grid ('.');
			}
		}
	}
	
	public void setLake () { // sets the lake to be '~'
		int i = 0, j;
		
		for (i = 1; i < 3; i++) {
			for (j = 3; j < 6; j++) {
				board[i][j].setTerrain('~');
			}
		}
		
		for (i = 4; i < 6; i++) {
			for (j = 3; j < 6; j++) {
				board[i][j].setTerrain('~');
			}
		}
	}
	
	public void setBases () {
		// left base
		board[2][0].setTerrain('#');
		board[3][0].setTerrain('@');
		board[3][1].setTerrain('#');
		board[4][0].setTerrain('#');
		
		// right base;
		board[2][8].setTerrain('#');
		board[3][8].setTerrain('@');
		board[3][7].setTerrain('#');
		board[4][8].setTerrain('#');
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
		board[0][0].setPiece(findPiece("T1"));
		board[6][8].setPiece(findPiece("T2"));
		
		board[0][2].setPiece(findPiece("E1"));
		board[6][6].setPiece(findPiece("E2"));
		
		board[1][1].setPiece(findPiece("C1"));
		board[5][7].setPiece(findPiece("C2"));
		
		board[2][2].setPiece(findPiece("W1"));
		board[4][6].setPiece(findPiece("W2"));
		
		board[4][2].setPiece(findPiece("LD1"));
		board[2][6].setPiece(findPiece("LD2"));
		
		board[5][1].setPiece(findPiece("D1"));
		board[1][7].setPiece(findPiece("D2"));
		
		board[6][2].setPiece(findPiece("R1"));
		board[0][6].setPiece(findPiece("R2"));
		
		board[6][0].setPiece(findPiece("LN1"));
		board[0][8].setPiece(findPiece("LN2"));
	}
	
	public Piece findPiece (String name) {
		for (Piece p: pieces) {
			if (p.getPieceName().equals(name))
				return p;
		}
		return null;
	}
	
	public void displayBoard () {
		int i, j;
		
		for (i = 0; i < board.length; i++) {
			for (j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j].getObject()+"\t");
			}
			System.out.println();
			System.out.println();
		}
	}

	public boolean getIsOpen (int r, int c) {
		if (board[r - 1][c - 1].getObject().equals('.'))
			return true;
		return false;
	}

	public boolean getIsLake (int r, int c) {
		if (board[r - 1][c - 1].getObject().equals('~'))
			return true;
		return false;
	}

	public Piece getPiece (int r, int c) {
		return board[r - 1][c - 1].getPiece();
	}
}
