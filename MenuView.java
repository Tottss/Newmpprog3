import java.awt.*;
import javax.swing.*;

public class MenuView extends JPanel {
    private JButton startButton, exitButton;
    private AppFrame appFrame;
    
    public MenuView(AppFrame appFrame) {
        this.appFrame = appFrame;
        setBackground(Color.GRAY);
        setLayout(new GridBagLayout());
        setSize(AppFrame.FULL_WIDTH, AppFrame.FULL_HEIGHT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
         
        JLabel title = new JLabel("Jungle King"); // add title
        title.setFont(new Font("Arial Unicode MS", Font.BOLD, 32));
        title.setForeground(Color.BLACK);
        gbc.gridy = 0;
        gbc.gridx = 0;
        add(title, gbc);
		
        startButton = new JButton("Start Game"); // add start button
        startButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));
        startButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 1;
        add(startButton, gbc);
		
        exitButton = new JButton("Exit Game"); // add exit button
        exitButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));
        exitButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 2;
        add(exitButton, gbc);
        
        // action listeners
        startButton.addActionListener(e -> appFrame.switchToAnimalSelection());
        exitButton.addActionListener(e -> System.exit(0));
        
        // decorative elements
        gbc.gridy = 3;
        add(Box.createVerticalStrut(50), gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
