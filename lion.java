

public class lion extends Piece {
    // constructor
    public lion(int playerNO){
        super("LN"+playerNO, 7, playerNO);
    }

	
    // checks if next grid is crossable
    @Override
    public boolean canCross() {
        return true; // Rat can cross if the space is a lake
    }

	
}
