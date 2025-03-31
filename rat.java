

public class rat extends Piece {
    // constructor
    
    public rat(int playerNo){
        super("R"+playerNo, 1, playerNo);
    }

    // checks if next grid is crossable

    @Override
    public boolean isStronger (Piece piece) {
		if (piece.getWeak())
			return true;
		else return piece.getStrength() == 8;
	}
	
	@Override
	public boolean canSwim(){
		return true;
	}

    
}
