public class Piece {
	protected String pieceName; // e.g., "R1", "C2", "LPD1"
	protected int strength; // strength ranges from 1 to 8
	protected int playerNo; // determines which pieces belong to either player 1 or 2
	protected boolean alive;
	protected boolean weak;
	protected int r; // position gets updated after a move
	protected int c; // position gets updated after a move
	
	public Piece (String pieceName, int strength, int playerNo) {
		this.pieceName = pieceName;
		this.strength = strength;
		this.playerNo = playerNo;
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
	
	public boolean canSwim(){
            // checking if animal can stay on lake tile :default false
            return this.pieceName.equals("R1") || this.pieceName.equals("R2");
	}
	
	public boolean canCross() {
        return this.pieceName.equals("LN1") || this.pieceName.equals("T1") || this.pieceName.equals("LN2") || this.pieceName.equals("T2"); // checking if animal can cross lake tile : default false
    }
	
	public void crossLake (String m) {
		if (m == "W")
			this.setPosition(this.getRow() - 3, this.getColumn());
		else if (m == "S")
			this.setPosition(this.getRow() + 3, this.getColumn());
		else if (m == "A")
			this.setPosition(this.getRow(), this.getColumn() - 4);
		else if (m == "D")
			this.setPosition(this.getRow(), this.getColumn() + 4);
    }

	public int getPlayerNumber(){
		return playerNo;
	}

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
