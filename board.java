import java.util.*;

public class Board { // initialize board and pieces
	private Grid[][] board;
	private ArrayList<Piece> pieces;

	public Board () {
		board = new Grid[7][9]; // initializes the 7x9 grid
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
				board[i][j] = new Grid ('.');
			}
		}
	}
	
	public void setLake () { // sets the lake to be '~'
		int i, j;
		
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
		board[0][0].setPiece(findPiece("T1"), 0, 0);
		board[6][8].setPiece(findPiece("T2"), 6, 8);
		
		board[0][2].setPiece(findPiece("E1"), 0, 2);
		board[6][6].setPiece(findPiece("E2"), 6, 6);
		
		board[1][1].setPiece(findPiece("C1"), 1, 1);
		board[5][7].setPiece(findPiece("C2"), 5, 7);
		
		board[2][2].setPiece(findPiece("W1"), 2, 2);
		board[4][6].setPiece(findPiece("W2"), 4, 6);
		
		board[4][2].setPiece(findPiece("LD1"), 4, 2);
		board[2][6].setPiece(findPiece("LD2"), 2, 6);
		
		board[5][1].setPiece(findPiece("D1"), 5, 1);
		board[1][7].setPiece(findPiece("D2"), 1, 7);
		
		board[6][2].setPiece(findPiece("R1"), 6, 2);
		board[0][6].setPiece(findPiece("R2"), 0, 6);
		
		board[6][0].setPiece(findPiece("LN1"), 6, 0);
		board[0][8].setPiece(findPiece("LN2"), 0, 8);
	}
	
	public Piece findPiece (String name) { // finds piece by its String name in array list
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
	
	// public void movePiece (Piece piece, char m) { // updates position of piece
		// int newR, newC;
		// int r = piece.getRow(), c = piece.getColumn();
		
		// /*
		// When a piece gets eaten:
		// - setDead()
		// - should not be visible on the board/gets replaced by the piece
		
		// When a piece goes to an opposing trap:
		// - piece gets weakened
		
		// When a piece goes to an opposing home base:
		// - game ends
		// - determine which player wins
		// */
		
		// if (board[r][c].getObject() instanceof piece) { // check if object in given pos is of Piece type
			// if (board[r][c].getPiece().getAlive()) { // piece chosen SHOULD be alive
				// if (m == 'W') {
					// if (isValidMove(piece, m)) {
						
					// }
				// }
				
				// else if (m == 'S') {
					// if (isValidMove(piece, m)) {
						// newR = piece.getRow()++;
						// newC = piece.getColumn();
					// }
				// }
				
				// else if (m == 'A') {
					// if (isValidMove(piece, m)) {
						// newR = piece.getRow();
						// newC = piece.getColumn()--;
					// }
				// }
				
				// else if (m == 'D') {
					// if (isValidMove(piece, m)) {
						// newR = piece.getRow();
						// newC = piece.getColumn()++;
					// }
				// }
			// }
		// }
		
		// piece.setPosition(newR, newC); // update positions
	// }
	
	// public boolean isValidMove (Piece piece, char m) { // checks if piece move is valid
		// /*
		// Moves can be invalid if:
		// - out of bounds (✓)
		// - space is already occupied by piece unless the piece can eat the other piece (✓)
		// - piece wishes to move to lake but can't swim (✓)
		// - piece wishes to cross lake but there's a rat(✓)
		// - piece wishes to move to its own home base (✓)
		// - piece wishes to move to its own traps (✓)
		// - piece wishes to move on a friendly piece's place (✓)
		
		// Other scenario moves that are valid:
		// - piece goes to trap
		// - piece leaves trap
		// - piece goes to home base
		// */
		// int currR = piece.getRow(), currC = piece.getColumn();
		// int newR = currR, newC = currC;
		
		// switch (m) {
			// case 'W':
				// newR--;
				// break;
			// case 'S': 
				// newR++; 
				// break;
			// case 'A': 
				// newC-- ; 
				// break;
			// case 'D': 
				// newC++; 
				// break;
			// default: 
				// return false; // invalid move input
		// }
		
		// if (!isWithinBounds(newR, newC)) // if out of bounds
			// return false;
		
		// Grid targetTile = board[newR][newC];
		
		// if (isRestrictedTile(piece, newR, newC)) // if targetTile is a friendly trap or home base
			// return false;
		
		// if (targetTile.getObject() == '~' && !piece.canSwim()) // if piece wants to go to lake but can't swim
			// return false;
		
		// if (piece.canCross() && !isLakeRowEmpty(newR, newC)) // if piece can cross but lakeRow is occupied with rat
			// return false; // todo: handle jumping over lake logic
		
		// if (targetTile.getObject() instanceof Piece) {
			// Piece targetPiece = targetTile.getPiece();
			// if (piece.getNumber() == targetPiece.getNumber()) // ensures it can only capture/move to opposing pieces
				// return false;
			// if (!piece.isStronger(targetPiece))
				// return false;
		// }
		
		
		// if (m == 'W') {
			// if (tempR - 1 >= 0) { // not out of bounds
				// if (board[tempR--][tempC].getObject() == '~') { // identify what the tile is (e.g., piece, lake)
					// piece.Rat.canSwim()
					// /*
					// 1st: piece cannot swim/cross lake
					// 2nd: piece can swim/cross lake
					// 3rd: piece can swim/cross lake but there's a rat
					// */
				// }
				// else if (board[tempR][tempC].getObject() instanceof piece) {
					// if (piece.isStronger(board[tempR][tempC].getObject()))
						// return true; // if piece can capture opposing piece
					// else
						// return false; // if piece can't capture opposing piece
				// }
				// else if (board[currR][currC].getObject().getNumber() == 1 && tempR == 2 && tempC == 0 ||
																			 // tempR == 3 && tempC == 0 ||
																			 // tempR == 4 && tempC == 0 ||
																			 // tempR == 3 && tempC == 1)
					// return false; // prevents player 1 from going to its own traps and home base
				// else if (board[currR][currC].getObject().getNumber() == 2 && tempR == 2 && tempC == 8 ||
																			 // tempR == 3 && tempC == 8 ||
																			 // tempR == 4 && tempC == 8 ||
																			 // tempR == 3 && tempC == 7)
					// return false; // prevents player 2 from going to its own traps and home base
			// }
		// }
	// }
	
	// public boolean isWithinBounds (int r, int c) {
		// return r >= 0 && r <= 6 && c >= 0 && c <= 8;
	// }
	
	// public boolean isRestrictedTile (Piece piece, int r, int c) { // ensures players don't go to their traps/bases
		// if (piece.getNumber() == 1) {
			// return (r == 2 && c == 0) ||
				   // (r == 3 && c == 0) ||
				   // (r == 4 && c == 0) ||
				   // (r == 3 && c == 1);
		// }
		
		// else if (piece.getNumber() == 2) {
			// return (r == 2 && c == 8) ||
				   // (r == 3 && c == 8) ||
				   // (r == 4 && c == 8) ||
				   // (r == 3 && c == 7);
		// }
		// return false;
	// }
	
	// public boolean isLakeRowEmpty (int r, int c) {
		// int i, j;
		
		// for (i = c; i < 3; i++) {
			// if (board[r][i].getPiece() != null) // if there's a piece (rat) in the lake tile)
					// return false;
		// }
		// return true;
	// }
	
	// public boolean isLakeColEmpty (int r, int c) {
		// int i, j;
		
		// for (i = 1; i < 3; i++) {
			// if (board[i][i].getPiece() != null) // if there's a piece (rat) in the lake tile)
					// return false;
		// }
		// return true;
	// }
	
	// public void trap (Piece piece) {
		// piece can't go to its own traps?
		// piece.setWeak();
	// }
	
	// public void homeBase (Piece piece) {
		// if (piece.getNumber() == 1)
			// determines which player wins
	// }
	
	// public void replaceObject () { // replaces whatever the piece occupies on the tile
		
	// }
}
