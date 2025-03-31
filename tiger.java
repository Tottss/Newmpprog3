public class tiger extends Piece {
    // constructor
    public tiger(int playerNo){
        super("tiger-" + (playerNo == 1 ? "blue" : "green"), 6, playerNo);
    }

	
    // checks if next grid is crossable
    @Override
    public boolean canCross() {
        return true; // Rat can cross if the space is a lake
    }

	
}
