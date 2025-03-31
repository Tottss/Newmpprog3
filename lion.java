
public class lion extends Piece {
    // constructor
    public lion(int playerNO){
        super('L', 7, playerNO);
    }

    public void crossLake (char m) {
	if (m == 'W')
		this.setPosition(this.getRow() - 3, this.getColumn());
	else if (m == 'S')
		this.setPosition(this.getRow() + 3, this.getColumn());
	else if (m == 'A')
		this.setPosition(this.getRow(), this.getColumn() - 4);
	else if (m == 'D')
		this.setPosition(this.getRow(), this.getColumn() + 4);
    }
	
    // checks if next grid is crossable
    @Override
    public boolean canCross(grid nextSpace) {
        return true; // Rat can cross if the space is a lake
    }

    @Override
    public boolean canKill(Piece target){
        return target.strength <= this.strength || target instanceof elephant;
    }
    
	
}
