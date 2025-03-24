import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuView extends JPanel {

    private JButton startButton, exitButton; // scoreButton?
    
    public MenuView (AppFrame app) {
        setBackground(Color.GRAY);
        setLayout(new GridBagLayout());
        setSize(AppFrame.FULL_WIDTH, AppFrame.FULL_HEIGHT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
    
        //Add title 
        JLabel title = new JLabel("Jungle King");
        title.setFont(new Font("Arial Unicode MS", Font.BOLD, 32));
        title.setForeground(Color.BLACK);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);


        //Add button to go to order view
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));
        gbc.gridy = 1;
        add(startButton, gbc);

        exitButton = new JButton("Exit Game");
        exitButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));
        gbc.gridy = 2;
        add(exitButton, gbc);
		
		startButton.addActionListener(e -> app.switchToGame());
		exitButton.addActionListener(e -> System.exit(0));
    }

    // public void setOrderButtonAction (ActionListener a) {
        // startButton.addActionListener(a);
    // }

    // public void setCloseButtonAction (ActionListener a) {
        // exitButton.addActionListener(a);
    // }
}
