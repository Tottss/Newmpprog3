package Newmpprog3;
public class Piece {
	protected String pieceName; // e.g., "R1", "C2", "LPD1"
	protected int strength; // strength ranges from 1 to 8
	protected int playerNo; // determines which pieces belong to either player 1 or 2
	protected boolean alive;
	protected boolean weak;
	protected int r; // position gets updated after a move
	protected int c; // position gets updated after a move
	

	/**
	 * Constructs a Piece object with the given attributes.
	 *
	 * @param pieceName The name of the piece.
	 * @param strength The strength value of the piece.
	 * @param playerNo The player number to which the piece belongs.
	 */
	public Piece (String pieceName, int strength, int playerNo) {
		this.pieceName = pieceName;
		this.strength = strength;
		this.playerNo = playerNo;
		alive = true;
		weak = false;
	}

	/**
	 * Retrieves the name of the piece.
	 *
	 * @return The piece name as a string.
	 */
	public String getPieceName(){
		return pieceName;
	}
	
	/**
	 * Retrieves the strength of the piece.
	 *
	 * @return The strength value of the piece.
	 */
	public int getStrength(){
		return strength;
	}

	/**
	 * Retrieves the player number associated with this piece.
	 *
	 * @return The player number.
	 */
	public int getNumber(){
		return playerNo;
	}

	/**
	 * Retrieves the row position of the piece.
	 *
	 * @return The row index.
	 */
	public int getRow () {
		return r;
	}
	
	/**
	 * Retrieves the column position of the piece.
	 *
	 * @return The column index.
	 */
	public int getColumn () {
		return c;
	}
	 
	/**
	 * Checks if the piece is currently in a weakened state.
	 *
	 * @return true if the piece is weak, false otherwise.
	 */
	public boolean getWeak () {
		return weak;
	}
	
	/**
	 * Checks if the piece is still alive.
	 *
	 * @return true if the piece is alive, false otherwise.
	 */
	public boolean getAlive () {
		return alive;
	}
	
	/**
	 * Sets the position of the piece on the board.
	 *
	 * @param r The new row position.
	 * @param c The new column position.
	 */
	public void setPosition (int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	/**
	 * Determines if the current piece can capture another piece.
	 * 
	 * @param piece The target piece to be captured.
	 * @return true if the piece is captured, false otherwise.
	 */
	public boolean capture (Piece piece) { // only captures, doesn't update position
		// return true if piece gets captured, false if piece doesn't get captured
		
		if (piece.getWeak()) {
			piece.setDead();
			return true;
		}
		
		else
			if (strength >= piece.getStrength()) {
				piece.setDead();
				return true;
			}
			else
				return false;
	}
	
	/**
	 * Determines if this piece is stronger than the given piece.
	 *
	 * @param piece The piece to compare strength against.
	 * @return true if this piece is stronger or the other piece is weak, false otherwise.
	 */
	public boolean isStronger (Piece piece) {
		if (piece.getWeak())
			return true;
		else return strength >= piece.getStrength();
	}

	/**
	 * Sets the player number of this piece.
	 *
	 * @param playerNo The new player number.
	 */
	public void setPlayerNumber(int playerNo){
		this.playerNo = playerNo;
	}
	
	/**
	 * Marks the piece as dead.
	 */
	public void setDead () {
		alive = false;
	}
	
	/**
	 * Marks the piece as weak.
	 */
	public void setWeak () {
		weak = true;
	}
	

	/**
	 * Removes the weakened status of the piece.
	 */
	public void setNotWeak () {
		weak = false;
	}

	/**
	 * Determines if the piece can stay on a lake tile.
	 * 
	 * @return true if the piece can stay on a lake, false otherwise.
	 */
	public boolean canSwim(){
            // checking if animal can stay on lake tile :default false
            //return this.pieceName.equals("R1") || this.pieceName.equals("R2");
			return false;
	}
	
	/**
	 * Determines if the piece can cross a lake.
	 * 
	 * @return true if the piece can cross a lake, false otherwise.
	 */
	public boolean canCross() {
        
		//return this.pieceName.equals("LN1") || this.pieceName.equals("T1") || this.pieceName.equals("LN2") || this.pieceName.equals("T2"); // checking if animal can cross lake tile : default false
		return false;
    }
	
	/**
	 * Retrieves the player number associated with the piece.
	 *
	 * @return The player number.
	 */
	public int getPlayerNumber(){
		return playerNo;
	}

	/**
	 * Determines if the piece has reached the opponent's base and won the game.
	 *
	 * @return true if the piece has reached the opponent's base, false otherwise.
	 */
	public boolean didWin(){
		if (this.r == 3 && this.c == 0 && playerNo == 2){
			return true;
		}
		if (this.r == 3 && this.c == 8 && playerNo == 1){
			return true;
		}
		return false;
	}
	
	@Override
	public String toString () {
		return pieceName;
	}
}
