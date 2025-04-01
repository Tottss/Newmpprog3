import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
	public int tileSize = 85;
	
	private static final int ROWS = 7;
    private static final int COLS = 9;
	
	private Board board;
	private ArrayList<Piece> pieces;
	
	public GameView () {
		this.setPreferredSize(new Dimension(COLS * tileSize, ROWS * tileSize));
		this.setBackground(Color.GREEN);
		
		board = new Board();
		pieces = new ArrayList<>();
		
		initializeBoard();
	}
	
	public void paintComponent (Graphics g) {
		Graphics g2d = (Graphics2D)g;
		
		int r, c;
		
		for (r = 0; r < ROWS; r++) {
			for (c = 0; c < COLS; c++) {
				g2d.setColor((c + r) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);
				g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
			}
		}
		
		for (Piece piece : pieces)
			piece.paint(g2d);
	}
}
