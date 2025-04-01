import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public class JungleKingBoard extends JPanel {
    public static final int ROWS = 7;
    public static final int COLS = 9;
    public static final int TILE_SIZE = 60; // Size of each tile in pixels
    private board board; // Changed from lowercase 'board' to follow conventions
    private ArrayList<Piece> pieces;
    public Piece selectedPiece = null;
    
    // Store images for better performance
    private Image lakeImage;
    private Image trapImage;
    private Image denBlueImage;
    private Image denGreenImage;
    private java.util.Map<String, Image> pieceImages = new java.util.HashMap<>();

    public JungleKingBoard() {
        setLayout(new BorderLayout());
        pieces = new ArrayList<>();
        board = new board(); // Assuming you rename your board class to Board
        
        // Load all images upfront
        loadImages();
        
        // Set preferred size based on tile size
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE));
        
        // Add mouse listener for interaction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / TILE_SIZE;
                int row = e.getY() / TILE_SIZE;
                handleTileClick(row, col);
            }
        });
    }

    private void loadImages() {
        lakeImage = loadImage("lake");
        trapImage = loadImage("trap");
        denBlueImage = loadImage("den-blue");
        denGreenImage = loadImage("den-green");
        String key = null;
        // Preload all piece images you expect to use
        // Add more as needed
        String[] pieceTypes = {"rat", "cat", "dog", "wolf", "leopard", 
                              "tiger", "lion", "elephant"};
        for (String type : pieceTypes) {
            for (int player = 1; player <= 2; player++) {
                if(player == 1)
					key = type + "-blue";
				if(player == 2)
					key = type + "-green";
                pieceImages.put(key, loadImage(key));
            }
        }
    }
    
    private Image loadImage(String fileName) {
        String path = "img/" + fileName + ".png";
        ImageIcon icon = new ImageIcon(path);
        return icon.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the board background
        drawBoardBackground(g);
        
        // Draw special tiles (lakes, traps, dens)
        drawSpecialTiles(g);
        
        // Draw pieces
        drawPieces(g);
        
        // Draw selection highlight if needed
        if (selectedPiece != null) {
            drawSelection(g, selectedPiece);
        }
    }
    
    private void drawBoardBackground(Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                // Alternate colors for checkerboard pattern
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.DARK_GRAY);
                }
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }
    
    private void drawSpecialTiles(Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Object cell = board.getGrid(row, col);
                
                if (cell instanceof Character) {
                    char terrain = (Character) cell;
                    if (terrain == '~') {
                        g.drawImage(lakeImage, col * TILE_SIZE, row * TILE_SIZE, this);
                    } else if (terrain == '#') {
                        g.drawImage(trapImage, col * TILE_SIZE, row * TILE_SIZE, this);
                    }
                }
            }
        }
        
        // Draw dens
        g.drawImage(denBlueImage, 0 * TILE_SIZE, 3 * TILE_SIZE, this);
        g.drawImage(denGreenImage, 8 * TILE_SIZE, 3 * TILE_SIZE, this);
    }
    
    private void drawPieces(Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Object cell = board.getGrid(row, col);
                if (cell instanceof Piece) {
                    Piece piece = (Piece) cell;
                    Image pieceImage = pieceImages.get(piece.getPieceName().toLowerCase());
                    if (pieceImage != null) {
                        g.drawImage(pieceImage, col * TILE_SIZE, row * TILE_SIZE, this);
                    }
                }
            }
        }
    }
    
    private void drawSelection(Graphics g, Piece piece) {
        g.setColor(Color.YELLOW);
        g.drawRect(piece.getColumn() * TILE_SIZE, piece.getRow() * TILE_SIZE, 
                  TILE_SIZE, TILE_SIZE);
        g.drawRect(piece.getColumn() * TILE_SIZE + 1, piece.getRow() * TILE_SIZE + 1, 
                  TILE_SIZE - 2, TILE_SIZE - 2);
    }
    
    private void handleTileClick(int row, int col) {
        // Handle piece selection/movement here
        Object cell = board.getGrid(row, col);
        
        if (cell instanceof Piece) {
            Piece clickedPiece = (Piece) cell;
            
            if (selectedPiece == null) {
                // Select the piece
                selectedPiece = clickedPiece;
            } else {
                // Try to move or capture
                if (selectedPiece.capture(clickedPiece)) {
                    // Handle capture
                    board.movePiece(selectedPiece, row, col);
                } else {
                    // Handle movement
                    board.movePiece(selectedPiece, row, col);
                }
                selectedPiece = null;
            }
        } else {
            // Move to empty space
            if (selectedPiece != null) {
                board.movePiece(selectedPiece, row, col);
                selectedPiece = null;
            }
        }
        
        repaint(); // Redraw the board
    }
    
    public Piece getPiece(int row, int col) {
        Object cell = board.getGrid(row, col);
        return (cell instanceof Piece) ? (Piece) cell : null;
    }
}
