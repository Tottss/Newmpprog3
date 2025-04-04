import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class AnimalSelectionGUI extends JPanel {
    private static final String BACK_IMAGE = "img/shou2025.png";
    private static final int ANIMAL_COUNT = 8;
    private AppFrame appFrame;
    private ArrayList<Piece> animals;
    private JLabel topLabel;
    private JButton[] animalButtons;
    private int selectionsMade = 0;
    private Piece player1Animal, player2Animal;
    private JLabel imageLabel;

    public AnimalSelectionGUI (AppFrame appFrame) {
        this.appFrame = appFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        initializeAnimals();
		
        JPanel topPanel = new JPanel(); // top panel for instructions
        topLabel = new JLabel("Player 1: Select an animal card", JLabel.CENTER);
        topLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(topLabel);
        add(topPanel, BorderLayout.NORTH);

        imageLabel = new JLabel("", JLabel.CENTER); // center panel for selected animal display
        imageLabel.setPreferredSize(new Dimension(300, 300));
        add(imageLabel, BorderLayout.CENTER);
		
        JPanel cardPanel = new JPanel(new GridLayout(2, 4, 15, 15)); // bottom panel for animal cards
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));
        cardPanel.setBackground(new Color(240, 240, 240));
        animalButtons = new JButton[ANIMAL_COUNT];
        
        try {
            ImageIcon backIcon = new ImageIcon(BACK_IMAGE);
            Image scaledBack = backIcon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
            ImageIcon scaledBackIcon = new ImageIcon(scaledBack);
            
            for (int i = 0; i < ANIMAL_COUNT; i++) {
                animalButtons[i] = new JButton(scaledBackIcon);
                animalButtons[i].setPreferredSize(new Dimension(120, 180));
                animalButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                animalButtons[i].setContentAreaFilled(false);
                animalButtons[i].addActionListener(new AnimalButtonListener(i));
                cardPanel.add(animalButtons[i]);
            }
        }
		catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading card images", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        add(cardPanel, BorderLayout.SOUTH);
    }

    private void initializeAnimals () { // add all animal pieces
        animals = new ArrayList<>();
        
        animals.add(new Piece("elephant-blue", 8, 0));
        animals.add(new Piece("lion-blue", 7, 0));
        animals.add(new Piece("tiger-blue", 6, 0));
        animals.add(new Piece("leopard-blue", 5, 0));
        animals.add(new Piece("wolf-blue", 4, 0));
        animals.add(new Piece("dog-blue", 3, 0));
        animals.add(new Piece("cat-blue", 2, 0));
        animals.add(new Piece("rat-blue", 1, 0));
        
        Collections.shuffle(animals);
    }

    private class AnimalButtonListener implements ActionListener {
        private int index;

        public AnimalButtonListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            if (animalButtons[index].isEnabled()) {
                selectionsMade++;
                Piece selectedAnimal = animals.get(index);
                
                try {
                    ImageIcon animalIcon = new ImageIcon("img/" + selectedAnimal.getPieceName() + ".png");
                    Image scaledAnimal = animalIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledAnimal));
                    
                    Image cardImage = animalIcon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
                    animalButtons[index].setIcon(new ImageIcon(cardImage));
                    animalButtons[index].setDisabledIcon(new ImageIcon(cardImage));
                    animalButtons[index].setEnabled(false);
                    
                    if (selectionsMade == 1) {
                        player1Animal = selectedAnimal;
                        topLabel.setText("Player 2: Select an animal card");
                    }
					else if (selectionsMade == 2) {
                        player2Animal = selectedAnimal;
                        determineFirstPlayer();
                    }
                }
				catch (Exception ex) {
                    JOptionPane.showMessageDialog(AnimalSelectionGUI.this, 
                        "Error loading animal image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void determineFirstPlayer() {
		
        for (JButton button : animalButtons) { // disable all cards
            button.setEnabled(false);
        }
    
        // determine first player
        int firstPlayer;
        String message;
        
        if (player1Animal.getStrength() > player2Animal.getStrength()) {
            firstPlayer = 1;
            message = "Player 1 (" + player1Animal.getPieceName() + ") goes first!";
        }
		else if (player2Animal.getStrength() > player1Animal.getStrength()) {
            firstPlayer = 2;
            message = "Player 2 (" + player2Animal.getPieceName() + ") goes first!";
        }
		else {
            firstPlayer = new Random().nextInt(2) + 1;
            message = "Equal strength! Randomly selecting... Player " + firstPlayer + " goes first!";
        }
    
        // create results panel
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(new Color(240, 240, 240));
        
        // create panel for selected cards
        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardsPanel.setBackground(new Color(240, 240, 240));
        
        // player 1's card
        JPanel player1Panel = new JPanel(new BorderLayout());
        player1Panel.setBackground(new Color(240, 240, 240));
        JLabel player1Label = new JLabel("Player 1's Card", JLabel.CENTER);
        player1Label.setFont(new Font("Arial", Font.BOLD, 18));
        
        ImageIcon player1Icon = new ImageIcon("img/" + player1Animal.getPieceName() + ".png");
        Image player1Image = player1Icon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        JLabel player1Card = new JLabel(new ImageIcon(player1Image));
        player1Card.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        
        player1Panel.add(player1Label, BorderLayout.NORTH);
        player1Panel.add(player1Card, BorderLayout.CENTER);
        
        // player 2's card
        JPanel player2Panel = new JPanel(new BorderLayout());
        player2Panel.setBackground(new Color(240, 240, 240));
        JLabel player2Label = new JLabel("Player 2's Card", JLabel.CENTER);
        player2Label.setFont(new Font("Arial", Font.BOLD, 18));
        
        ImageIcon player2Icon = new ImageIcon("img/" + player2Animal.getPieceName() + ".png");
        Image player2Image = player2Icon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        JLabel player2Card = new JLabel(new ImageIcon(player2Image));
        player2Card.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        
        player2Panel.add(player2Label, BorderLayout.NORTH);
        player2Panel.add(player2Card, BorderLayout.CENTER);
        
        // add cards to panel
        cardsPanel.add(player1Panel);
        cardsPanel.add(player2Panel);
        
        // result message
        JLabel resultLabel = new JLabel(message, JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        // continue button
        JButton continueButton = new JButton("START GAME");
        continueButton.setFont(new Font("Arial", Font.BOLD, 18));
        continueButton.setPreferredSize(new Dimension(200, 50));
        continueButton.addActionListener(e -> appFrame.startGameWithFirstPlayer(firstPlayer));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(continueButton);
        
        // add components to result panel
        resultPanel.add(resultLabel, BorderLayout.NORTH);
        resultPanel.add(cardsPanel, BorderLayout.CENTER);
        resultPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        // show results
        removeAll();
        setLayout(new BorderLayout());
        add(resultPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
