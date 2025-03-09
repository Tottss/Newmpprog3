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
		// board[2][0].setTerrain('#');
		board[3][0].setTerrain('@');
		// board[3][1].setTerrain('#');
		// board[4][0].setTerrain('#');
		
		// right base;
		// board[2][8].setTerrain('#');
		board[3][8].setTerrain('@');
		// board[3][7].setTerrain('#');
		// board[4][8].setTerrain('#');
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
		// board[0][0].setPiece(findPiece("T1"), 0, 0);
		// board[6][8].setPiece(findPiece("T2"), 6, 8);
		
		// board[0][2].setPiece(findPiece("E1"), 0, 2);
		// board[6][6].setPiece(findPiece("E2"), 6, 6);
		
		board[1][1].setPiece(findPiece("C1"), 1, 1);
		board[5][7].setPiece(findPiece("C2"), 5, 7);
		
		// board[2][2].setPiece(findPiece("W1"), 2, 2);
		// board[4][6].setPiece(findPiece("W2"), 4, 6);
		
		// board[4][2].setPiece(findPiece("LD1"), 4, 2);
		// board[2][6].setPiece(findPiece("LD2"), 2, 6);
		
		board[5][1].setPiece(findPiece("D1"), 5, 1);
		board[1][7].setPiece(findPiece("D2"), 1, 7);
		
		// board[6][2].setPiece(findPiece("R1"), 6, 2);
		// board[0][6].setPiece(findPiece("R2"), 0, 6);
		
		// board[6][0].setPiece(findPiece("LN1"), 6, 0);
		// board[0][8].setPiece(findPiece("LN2"), 0, 8);
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
	
	public boolean movePiece (Piece piece, String m) { // updates position of piece; returns value of isValidMove()
		int oldR = piece.getRow(), oldC = piece.getColumn(), newR = oldR, newC = oldC;
		boolean valid;
		
		/*
		When a piece gets eaten:
		- setDead()
		- should not be visible on the board/gets replaced by the piece
		
		When a piece goes to an opposing trap:
		- piece gets weakened
		
		When a piece goes to an opposing home base:
		- game ends
		- determine which player wins
		
		When a piece moves:
		- DON'T FORGET TO CLEAR OLD POSITION (whatever was there, turn it into that terrain)
		*/
		
		if (!piece.getAlive()) // piece chosen SHOULD be alive
			return false;
			
		if (!isValidMove(piece, m))
			return false;
		
		switch (m) {
			case "W":
				newR--;
				break;
			case "S": 
				newR++; 
				break;
			case "A": 
				newC-- ; 
				break;
			case "D": 
				newC++; 
				break;
			default: 
				return false; // invalid move input
		}
		
		Grid targetTile = board[newR][newC];
		
		if (targetTile.getObject().equals('~') && piece.canCross()) { // for lions and tigers only
			piece.crossLake(m); // note: this already updates the piece's position
			
			if (targetTile.getObject() instanceof Piece) { // if resulting tile contains an opposing piece
				Piece targetPiece = targetTile.getPiece();
				
				if (piece.capture(targetPiece)) { // piece is stronger/as strong as
					piece.setPosition(newR, newC);
					targetTile.setPiece(piece, newR, newC);
				}
				else // piece is weaker
					piece.setPosition(oldR, oldC); // reverts to original position
			}
			
			else
				board[newR][newC].setPiece(piece, newR, newC); // there's no piece on resulting tile
		}
		
		if (targetTile.getObject() instanceof Piece) { // capturing opposing piece
			Piece targetPiece = targetTile.getPiece();
			
			if (piece.capture(targetPiece)) { // capture piece
				piece.setPosition(newR, newC); // update position of piece
				targetTile.setPiece(piece, newR, newC); // move piece on board
			}
		}
		
		piece.setPosition(newR, newC); // update positions
		// after moving, set the old position back to its original object
		board[oldR][oldC].setPiece(null, -1, -1);
		board[newR][newC].setPiece(piece, newR, newC); // update object on board to its new position
		return isValidMove(piece, m);
	}
	
	public boolean isValidMove (Piece piece, String m) { // checks if piece move is valid
		/*
		Moves can be invalid if:
		- out of bounds (✓)
		- space is already occupied by piece unless the piece can eat the other piece (✓)
		- piece wishes to move to lake but can't swim (✓)
		- piece wishes to cross lake but there's a rat(✓)
		- piece wishes to move to its own home base (✓)
		- piece wishes to move to its own traps (✓)
		- piece wishes to move on a friendly piece's place (✓)
		
		Other scenario moves that are valid:
		- piece moves to an empty tile
		- piece captures opposing piece (capture is valid)
		- piece swims (rat)
		- piece crosses lake (lion/tiger) and lake is clear of rats
		- piece crosses lake (lion/tiger) and lake is clear of rats AND captures opposing piece
		
		- piece goes to opposing trap (update piece status: weakened)
		- piece leaves opposing trap (update piece status: not weak anymore)
		- piece goes to opponent's home base
		*/
		int currR = piece.getRow(), currC = piece.getColumn();
		int newR = currR, newC = currC;
		
		switch (m) {
			case "W":
				newR--;
				break;
			case "S": 
				newR++; 
				break;
			case "A": 
				newC-- ; 
				break;
			case "D": 
				newC++; 
				break;
			default: 
				return false; // invalid move input
		}
		
		if (!isWithinBounds(newR, newC)) // if out of bounds
			return false;
		
		Grid targetTile = board[newR][newC];
		
		if (isRestrictedTile(piece, newR, newC)) // if targetTile is a friendly trap or home base
			return false;
		
		if (targetTile.getObject().equals('~') && !piece.canSwim()) // if piece wants to go to lake but can't swim
			return false;
		
		// if piece can cross but lake row/col is occupied with rat
		if (targetTile.getObject().equals('~') && piece.canCross() && !isLakeRowEmpty(newR))
			return false;
		
		if (targetTile.getObject().equals('~') && piece.canCross() && !isLakeColEmpty(newR, currC))
			return false;
		
		if (targetTile.getObject() instanceof Piece) {
			Piece targetPiece = targetTile.getPiece();
			if (piece.getNumber() == targetPiece.getNumber()) // ensures it can only capture/move to opposing pieces
				return false;
			if (!piece.isStronger(targetPiece))
				return false;
		}
		return true;
	}
	
	public boolean isWithinBounds (int r, int c) {
		return r >= 0 && r <= 6 && c >= 0 && c <= 8;
	}
	
	public boolean isRestrictedTile (Piece piece, int r, int c) { // ensures players don't go to their traps/bases
		// UNCOMENT ONCE TRAPS ARE BACK
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
		if (piece.getNumber() == 1)
			return r == 3 && c == 0; // only home base
		else if (piece.getNumber() == 2)
			return r == 3 && c == 8;
		
		return false;
	}
	
	public boolean isLakeRowEmpty (int r) { // r = newR; checks if lakeRow is clear of rats
		int i;
		
		for (i = 3; i <= 5; i++) {
			if (board[r][i].getPiece() != null) // if there's a piece (rat) in the lake tile)
					return false;
		}
		
		return true;
	}
	
	public boolean isLakeColEmpty (int r, int c) { // c = currC, r = newR
		int i;
		
		if (r == 1 || r == 2) {
			for (i = 1; i <= 2; i++) {
				if (board[i][c].getPiece() != null) // if there's a piece (rat) in the lake tile)
						return false;
			}
		}
		
		else if (r == 4 || r == 5) {
			for (i = 4; i <= 5; i++) {
				if (board[i][c].getPiece() != null) // if there's a piece (rat) in the lake tile)
						return false;
			}
		}
		
		
		return true;
	}
	
	public Piece searchforPiece(String pieceName, int playerNo){
		int row,col;

		for (row = 0; row < 7; row++){
			for (col = 0; col < 9; col++){
				if(board[row][col].getPiece().getNumber() == playerNo && board[row][col].getPiece().getPieceName().equals(pieceName)){
					return board[row][col].getPiece();
				}
			}
		}
		return null;
	}
	
	// public void trap (Piece piece) {
		//piece can't go to its own traps?
		//piece.setWeak();
	// }
	
	// public void homeBase (Piece piece) {
		// if (piece.getNumber() == 1)
			//determines which player wins
	// }
	
	// public void replaceObject () { // replaces whatever the piece occupies on the tile
		
	// }
}
