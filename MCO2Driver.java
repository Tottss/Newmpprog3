import javax.swing.*;

public class MCO2Driver {
    
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // initialize the game on swing's event dispatch thread
            clearScreen();
            System.out.println("Starting Jungle King Game...");
            
            AppFrame appFrame = new AppFrame(); // create and show the main application window
            System.out.println("Main window initialized");
            
            // game automatically starts with menu screen
            // (actual game loop is event-driven through button clicks)
        });
    }
  }
