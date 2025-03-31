package Newmpprog3;

public class rat extends Piece {
    // constructor
    public rat(){
        super("R", 1);
    }

    // checks if next grid is crossable
    @Override
    public boolean canCross(Piece piece) {
        return true; // Rat can cross if the space is a lake
    }

    @Override
    public boolean canKill(Piece target){
        return target instanceof elephant;
    }
	
	@Override
	public boolean canSwim(Piece piece){
		return true;
	}

    
}
