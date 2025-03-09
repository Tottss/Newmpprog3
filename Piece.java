
package mpprog3;


public class Piece {
	protected String pieceName; // e.g., "R1", "C2", "LPD1"
	protected int strength; // strength ranges from 1 to 8
	protected int playerNo; // determines which pieces belong to either player 1 or 2
	protected boolean alive;
	protected boolean weak;
	protected int r; // position gets updated after a move
	protected int c; // position gets updated after a move
	
	public Piece (String pieceName, int strength) {
		this.pieceName = pieceName;
		this.strength = strength;
		alive = true;
		weak = false;
	}

	public String getPieceName(){
		return pieceName;
	}
	
	public int getStrength(){
		return strength;
	}

	public int getNumber(){
		return playerNo;
	}

	public int getRow () {
		return r;
	}
	
	public int getColumn () {
		return c;
	}
	
	public boolean getWeak () {
		return weak;
	}
	
	public boolean getAlive () {
		return alive;
	}
	
	public void setPosition (int r, int c) {
		this.r = r;
		this.c = c;
	}
	
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
	
	public boolean isStronger (Piece piece) {
		if (piece.getWeak())
			return true;
		else if (strength >= piece.getStrength())
			return true;
		else
			return false;
	}

	public void setPlayerNumber(int playerNo){
		this.playerNo = playerNo;
	}
	
	public void setDead () {
		alive = false;
	}
	
	public void setWeak () {
		weak = true;
	}
	
	public void setNotWeak () {
		weak = false;
	}
	
	public boolean canSwim(Piece piece){
		return false; // checking if animal can stay on lake tile :default false
	}
	
	public boolean canCross(Piece piece) {
        return false; // checking if animal can cross lake tile : default false
    }

	@Override
	public String toString () {
		return pieceName;
	}
}
