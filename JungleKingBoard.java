import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public class JungleKingBoard extends JPanel {
    public static final int ROWS = 7;
    public static final int COLS = 9;
    public static final int TILE_SIZE = 100;
    private board board;
    private ArrayList<Piece> pieces;
    public Piece selectedPiece = null;
    private int currentPlayer = -1;
    private JLabel turnLabel;
    
    private Image lakeImage, trapImage, denBlueImage, denGreenImage;
    private java.util.Map<String, Image> pieceImages = new java.util.HashMap<>();

    public JungleKingBoard(int turn) {
        setLayout(new BorderLayout());
        pieces = new ArrayList<>();
        board = new board();
		currentPlayer = turn;
        
        turnLabel = new JLabel("Player " + turn + "s Turn", JLabel.CENTER); // turn indicator
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(turnLabel, BorderLayout.NORTH);
        
        loadImages();
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / TILE_SIZE;
                int row = e.getY() / TILE_SIZE;
                handleTileClick(row, col);
            }
        });
    }

    private void loadImages () {
        lakeImage = loadImage("lake");
        trapImage = loadImage("trap");
        denBlueImage = loadImage("den-blue");
        denGreenImage = loadImage("den-green");
        String key = null;
        
        String[] pieceTypes = {"rat", "cat", "dog", "wolf", "leopard", "tiger", "lion", "elephant"};
        for (String type : pieceTypes) {
            for (int player = 1; player <= 2; player++) {
                if (player == 1)
					key = type + "-blue";
				if (player == 2)
					key = type + "-green";
                pieceImages.put(key, loadImage(key));
            }
        }
    }
    
    private Image loadImage (String fileName) {
        String path = "img/" + fileName + ".png";
        ImageIcon icon = new ImageIcon(path);
        return icon.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        
        drawBoardBackground(g); // background
        
        drawSpecialTiles(g); // lake, traps, dens
        
        drawPieces(g); // pieces
        
        if (selectedPiece != null) { // highlight
            drawSelection(g, selectedPiece);
        }
    }
    
    private void drawBoardBackground (Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                
                if ((row + col) % 2 == 0) // alternate colors
                    g.setColor(Color.LIGHT_GRAY);
                
				else
                    g.setColor(Color.DARK_GRAY);
				
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }
    
    private void drawSpecialTiles (Graphics g) {
		int row, col;
		Object cell;
		
        for (row = 0; row < ROWS; row++) {
            for (col = 0; col < COLS; col++) {
                cell = board.getGrid(row, col);
                
                if (cell instanceof Character) {
                    char terrain = (Character) cell;
                    if (terrain == '~') // lake
                        g.drawImage(lakeImage, col * TILE_SIZE, row * TILE_SIZE, this);
                    
					else if (terrain == '#') // traps
                        g.drawImage(trapImage, col * TILE_SIZE, row * TILE_SIZE, this);
                    
                }
            }
        }
        // dens
        g.drawImage(denBlueImage, 0 * TILE_SIZE, 3 * TILE_SIZE, this);
        g.drawImage(denGreenImage, 8 * TILE_SIZE, 3 * TILE_SIZE, this);
    }
    
    private void drawPieces (Graphics g) {
		int row, col;
		Object cell;
		Piece piece;
		Image pieceImage;
		
        for (row = 0; row < ROWS; row++) {
            for (col = 0; col < COLS; col++) {
                cell = board.getGrid(row, col);
                if (cell instanceof Piece) {
                    piece = (Piece) cell;
                    pieceImage = pieceImages.get(piece.getPieceName().toLowerCase());
					
                    if (pieceImage != null)
                        g.drawImage(pieceImage, col * TILE_SIZE, row * TILE_SIZE, this);
                }
            }
        }
    }
    
    private void drawSelection (Graphics g, Piece piece) {
        g.setColor(Color.YELLOW);
        g.drawRect(piece.getColumn() * TILE_SIZE, piece.getRow() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g.drawRect(piece.getColumn() * TILE_SIZE + 1, piece.getRow() * TILE_SIZE + 1, TILE_SIZE - 2, TILE_SIZE - 2);
    }
    
    private void handleTileClick (int row, int col) {
		Object cell = board.getGrid(row, col);
		Piece clickedPiece;
		
		if (cell instanceof Piece) { // if clicking on a piece
			clickedPiece = (Piece) cell;
			
			if (selectedPiece == null) { // only allow selecting own pieces
				if (clickedPiece.getPlayerNumber() == currentPlayer)
					selectedPiece = clickedPiece;
			} 
			else {
				
				if (clickedPiece == selectedPiece) // deselect by clicking on the same piece
					selectedPiece = null;
				
				else if (isAdjacent(selectedPiece.getRow(), selectedPiece.getColumn(), row, col)) { // check if adjacent
					
					if (selectedPiece.getPlayerNumber() == clickedPiece.getPlayerNumber()) // attempt capture/move
						selectedPiece = clickedPiece; // clicked on own piece; change selection
					
					else { // attempt capture
						if (selectedPiece.capture(clickedPiece) && board.isValidMove(selectedPiece, row, col)) {
							board.movePiece(selectedPiece, row, col);
							board.trapped(selectedPiece);
							board.displayBoard();
							endTurn();
						}
					}
				}
			}
		} 
		else { // clicking on empty space
			if (selectedPiece != null && isAdjacent(selectedPiece.getRow(), selectedPiece.getColumn(), row, col)) {
				if (board.isValidMove(selectedPiece, row, col)) {
					board.movePiece(selectedPiece, row, col);
					board.trapped(selectedPiece);
			
					board.displayBoard();
					endTurn();
				}
			}
		}
		
		repaint();
	}
	
	private boolean isAdjacent(int row1, int col1, int row2, int col2) { // check if two positions are adjacent
		int rowDiff = Math.abs(row1 - row2);
		int colDiff = Math.abs(col1 - col2);
		
		// either same row, adjacent column, or same column, adjacent row
		return (rowDiff == 0 && colDiff == 1) || (rowDiff == 1 && colDiff == 0);
	}
    
    private void endTurn() {
        selectedPiece = null;
		if (checkWinCondition()) {
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            resetGame();
        }
        currentPlayer = (currentPlayer == 1) ? 2 : 1; // switch player
        updateTurnLabel();
        
        // check for win condition
        
    }
    
    private void updateTurnLabel() {
        turnLabel.setText("Player " + currentPlayer + "'s Turn");
        turnLabel.setForeground(currentPlayer == 1 ? Color.BLUE : Color.GREEN);
    }
    
    private boolean checkWinCondition() {
        // check if any player reached the opponent's den
        Piece blueDenPiece = getPiece(3, 0);
        Piece greenDenPiece = getPiece(3, 8);
        
        if (blueDenPiece instanceof Piece && blueDenPiece.getPlayerNumber() == 2) {
            return true; // player 2 reached blue den
        }
        if (greenDenPiece instanceof Piece && greenDenPiece.getPlayerNumber() == 1) {
            return true; // player 1 reached green den
        }
        return false;
    }
    
    private void resetGame() { // reset game state
        board = new board();
        currentPlayer = 1;
        selectedPiece = null;
        updateTurnLabel();
        repaint();
    }
    
    public Piece getPiece(int row, int col) {
        Object cell = board.getGrid(row, col);
        return (cell instanceof Piece) ? (Piece) cell : null;
    }
}
