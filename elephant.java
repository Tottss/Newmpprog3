

public class elephant extends Piece {

    // constructor
    public elephant(int playerNo) {
        super("elephant-" + (playerNo == 1 ? "blue" : "green"), 8,playerNo);
    }

    // checks if next grid is crossable
    @Override
    public boolean isStronger (Piece piece) {
        if (piece instanceof rat){
            return false;
        }
        return strength >= piece.getStrength();
    }

    @Override
    public boolean capture (Piece piece) { // only captures, doesn't update position
		// return true if piece gets captured, false if piece doesn't get captured
		
		if (piece.getWeak()) {
			piece.setDead();
			return true;
		}
		
		else
			if (strength >= piece.getStrength() && !(piece instanceof rat)) {
				piece.setDead();
				return true;
			}
			else
				return false;
	}
}
