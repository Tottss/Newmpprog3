public class move {
    int oldCol;
    int oldRow;
    int newCol;
    int newRow;

    Piece piece;
    Piece capture;

    public move(JungleKingBoard board,Piece piece,int newCol,int newRow){
        this.oldCol = piece.getColumn();
        this.oldRow = piece.getRow();
        this.newCol = newCol;
        this.newRow = newRow;

        this.piece = piece;
        this.capture = board.getPiece(newCol, newRow);
    }

}
