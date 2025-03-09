package mpprog3;

public class lion extends Piece {
    // constructor
    public lion(){
        super('L', 7);
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