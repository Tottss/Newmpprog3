package mpprog3;

public class grid {
	private Piece piece;
	private char terrain; // ~, #, @, .
	
	public grid (char terrain) {
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
	
	public char getTerrain () {
		return terrain;
	}
	
	public void setPiece (Piece piece) {
		this.piece = piece;
	}
	
	public void setTerrain (char terrain) {
		this.terrain = terrain;
	}
}
