import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    static int FULL_HEIGHT = 600;
    static int FULL_WIDTH = 800;
	
	private CardLayout cardLayout;
	private JPanel mainPanel;
	private MenuView menuView;
	private JungleKingBoard gameView;

    public AppFrame(){
        setTitle("Jungle King");
		setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		menuView = new MenuView(this);
		gameView = new JungleKingBoard();
		
		mainPanel.add(menuView, "Menu");
		mainPanel.add(gameView, "Game");
		
		add(mainPanel);
		setVisible(true);
    }
	
	public void switchToGame () {
		cardLayout.show(mainPanel, "Game");
	}

}
