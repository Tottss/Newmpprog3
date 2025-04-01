package Newmpprog3;

import java.awt.*;
import javax.swing.*;

public class AppFrame extends JFrame {
    static int FULL_HEIGHT = 600;
    static int FULL_WIDTH = 800;
	private int firstPlayer;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MenuView menuView;
    private AnimalSelectionGUI animalSelectionView;
    private JungleKingBoard gameView;

    public AppFrame() {
        setTitle("Jungle King");
        setSize(1100, 735);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        menuView = new MenuView(this);
        animalSelectionView = new AnimalSelectionGUI(this);
        gameView = null; // Will be initialized after selection
        
        mainPanel.add(menuView, "Menu");
        mainPanel.add(animalSelectionView, "AnimalSelection");
        
        add(mainPanel);
        setVisible(true);
    }
    
	public void returnToMenu() {
        cardLayout.show(mainPanel, "Menu");
    }

    public void switchToAnimalSelection() {
        cardLayout.show(mainPanel, "AnimalSelection");
    }
    
    public void startGameWithFirstPlayer(int firstPlayer) {
        if (gameView != null) {
            mainPanel.remove(gameView);
        }
		this.firstPlayer = firstPlayer;
        gameView = new JungleKingBoard(firstPlayer, this);
        mainPanel.add(gameView, "Game");
        cardLayout.show(mainPanel, "Game");
    }

    public void switchToMenu() {
        cardLayout.show(mainPanel, "Menu");
    }

    public void resetGame() {
        if (gameView != null) {
            mainPanel.remove(gameView);
        }
        gameView = new JungleKingBoard(firstPlayer,this);
        mainPanel.add(gameView, "Game");
    }
}