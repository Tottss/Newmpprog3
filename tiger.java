public class tiger extends Piece {
    // constructor
    public tiger(int playerNO){
        super("L"+playerNO, 6, playerNO);
    }

	
    // checks if next grid is crossable
    @Override
    public boolean canCross() {
        return true; // Rat can cross if the space is a lake
    }

	
}