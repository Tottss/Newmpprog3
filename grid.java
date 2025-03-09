public class Grid {
	private Piece piece;
	private char terrain; // ~, #, @, .
	
	public Grid (char terrain) {
		this.terrain = terrain;
	}
	
	public boolean isEmpty () {
		if (piece == null)
			return true;
		return false;
	}
	
	public Object getObject () {
		if (piece != null)
			return piece;
		return terrain;
	}
	
	public Piece getPiece () {
		return piece;
	}

	public void setNull(){
		this.piece = null;
	}
	
	public char getTerrain () {
		return terrain;
	}
	
	public void setPiece (Piece piece, int r, int c) { // sets a piece onto a specific grid in board
		this.piece = piece;
		this.piece.setPosition(r, c); // updates r and c of the piece
	}
	
	public void setTerrain (char terrain) {
		this.terrain = terrain;
	}
}
