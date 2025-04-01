
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class input extends MouseAdapter{
    private JungleKingBoard board;
    private Component clickedComponent;
    private int tileWidth;
    private int tileHeight;
    public input(JungleKingBoard board){
        this.board = board;
    }
    @Override
    public void mousePressed(MouseEvent e){
        // Get the component that was clicked
    
    clickedComponent = e.getComponent();
    
    tileWidth = clickedComponent.getWidth() / JungleKingBoard.COLS;
    
    // Calculate the column
    int col = e.getX() / tileWidth;
    
    // Similarly for row (if needed)
    tileHeight = clickedComponent.getHeight() / JungleKingBoard.ROWS;
    int row = e.getY() / tileHeight;
    
    // Make sure the column is within bounds
    col = Math.max(0, Math.min(col, JungleKingBoard.COLS - 1));
    
    Piece piecexy = board.getPiece(col, row);
    if (piecexy != null){
        board.selectedPiece = piecexy;
    }

    }
    @Override
    public void mouseReleased(MouseEvent e){
        
    }
    @Override
    public void mouseDragged(MouseEvent e){
        
    }
    
}
