import javax.swing.*;

public class MCO2Driver {
    
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
  
    public static void main(String[] args) {
        // Initialize the game on Swing's Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            clearScreen();
            System.out.println("Starting Jungle King Game...");
            
            // 1. Create and show the main application window
            AppFrame appFrame = new AppFrame();
            System.out.println("Main window initialized");
            
            // 2. Game automatically starts with menu screen
            // (Actual game loop is event-driven through button clicks)
            
            // Optional: Pre-load resources here if needed
        });
        
        // Explanation of the "hidden" game loop:
        // - After this point, Swing takes over
        // - The Event Dispatch Thread continuously:
        //   1. Listens for user input (clicks, key presses)
        //   2. Dispatches events to the appropriate listeners
        //   3. Repaints the UI when needed
        // - This happens automatically until the window is closed
    }
  }