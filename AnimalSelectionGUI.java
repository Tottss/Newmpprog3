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

    public AnimalSelectionGUI(AppFrame appFrame) {
        this.appFrame = appFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        initializeAnimals();

        // Top panel for instructions
        JPanel topPanel = new JPanel();
        topLabel = new JLabel("Player 1: Select an animal card", JLabel.CENTER);
        topLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(topLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center panel for selected animal display
        imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 300));
        add(imageLabel, BorderLayout.CENTER);

        // Bottom panel for animal cards
        JPanel cardPanel = new JPanel(new GridLayout(2, 4, 15, 15));
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading card images", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        add(cardPanel, BorderLayout.SOUTH);
    }

    private void initializeAnimals() {
        animals = new ArrayList<>();
        // Add all animal pieces
        animals.add(new Piece("elephant-green", 8, 0));
        animals.add(new Piece("lion-green", 7, 0));
        animals.add(new Piece("tiger-green", 6, 0));
        animals.add(new Piece("leopard-green", 5, 0));
        animals.add(new Piece("wolf-green", 4, 0));
        animals.add(new Piece("dog-green", 3, 0));
        animals.add(new Piece("cat-green", 2, 0));
        animals.add(new Piece("rat-green", 1, 0));
        
        Collections.shuffle(animals);
    }

    private class AnimalButtonListener implements ActionListener {
        private int index;

        public AnimalButtonListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
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
                    } else if (selectionsMade == 2) {
                        player2Animal = selectedAnimal;
                        determineFirstPlayer();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AnimalSelectionGUI.this, 
                        "Error loading animal image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void determineFirstPlayer() {
        // Disable all cards
        for (JButton button : animalButtons) {
            button.setEnabled(false);
        }

        // Determine first player
        int firstPlayer;
        String message;
        
        if (player1Animal.getStrength() > player2Animal.getStrength()) {
            firstPlayer = 1;
            message = "Player 1 (" + player1Animal.getPieceName() + ") goes first!";
        } else if (player2Animal.getStrength() > player1Animal.getStrength()) {
            firstPlayer = 2;
            message = "Player 2 (" + player2Animal.getPieceName() + ") goes first!";
        } else {
            firstPlayer = new Random().nextInt(2) + 1;
            message = "Equal strength! Randomly selecting... Player " + firstPlayer + " goes first!";
        }

        // Show results panel
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(new Color(240, 240, 240));
        
        JLabel resultLabel = new JLabel(message, JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultPanel.add(resultLabel, BorderLayout.CENTER);
        
        JButton continueButton = new JButton("START GAME");
        continueButton.setFont(new Font("Arial", Font.BOLD, 18));
        continueButton.setPreferredSize(new Dimension(200, 50));
        continueButton.addActionListener(e -> appFrame.startGameWithFirstPlayer(firstPlayer));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(continueButton);
        resultPanel.add(buttonPanel, BorderLayout.SOUTH);

        removeAll();
        setLayout(new BorderLayout());
        add(resultPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}