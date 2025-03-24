import java.util.*;

import javax.swing.*;

public class MCO2Driver {
    
  
  public static void clearScreen() {  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
}
  
    public static void main(String[] args) {
		// SwingUtilities.invokeLater(() -> {
			// JungleKingBoard board = new JungleKingBoard();
			// board.setVisible(true);
		// });
		SwingUtilities.invokeLater(AppFrame::new);
    }

}
