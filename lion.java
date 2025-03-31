

public class lion extends Piece {
    // constructor
    public lion(int playerNO){
        super("lion-" + (playerNo == 1 ? "blue" : "green"), 7, playerNo);
    }

	
    // checks if next grid is crossable
    @Override
    public boolean canCross() {
        return true; // Rat can cross if the space is a lake
    }

	
}
