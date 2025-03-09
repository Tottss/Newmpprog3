package mpprog3;

public class elephant extends Piece {
    // constructor
    public elephant(){
        super('E', 8);
    }

    // checks if next grid is crossable

    @Override
    public boolean canKill(Piece target){
        return target.strength <= this.strength || !(target instanceof rat);
    }
    
}