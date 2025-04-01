import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class AnimalSelectionGUI extends JFrame {
    private static final String DEFAULT_IMAGE = null;
    private static final String BACK_IMAGE = "img/shou2025.png"; // Image for face-down cards
    private static final int ANIMAL_COUNT = 8;
    private List<Piece> animals;
    private JLabel imageLabel;
    private JLabel topLabel;
    private JButton[] animalButtons;
    private int selectionsMade = 0;
    private Piece player1Animal, player2Animal;

    public AnimalSelectionGUI() {
        setTitle("Animal Selection");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeAnimals();

        // Top panel for instructions
        JPanel topPanel = new JPanel();
        topLabel = new JLabel("Player 1: Select an animal card", JLabel.CENTER);
        topLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(topLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center panel for main image display
        imageLabel = new JLabel(new ImageIcon(DEFAULT_IMAGE));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        // Bottom panel for animal cards
        JPanel cardPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        animalButtons = new JButton[ANIMAL_COUNT];
        
        // Load card back image (should be 200x300 pixels for example)
        ImageIcon backIcon = new ImageIcon(BACK_IMAGE);
        Image scaledBack = backIcon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledBackIcon = new ImageIcon(scaledBack);
        
        for (int i = 0; i < ANIMAL_COUNT; i++) {
            animalButtons[i] = new JButton(scaledBackIcon);
            animalButtons[i].setPreferredSize(new Dimension(150, 200));
            animalButtons[i].setBorder(BorderFactory.createEmptyBorder());
            animalButtons[i].setContentAreaFilled(false);
            animalButtons[i].addActionListener(new AnimalButtonListener(i));
            cardPanel.add(animalButtons[i]);
        }
        
        add(cardPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
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
                
                // Load and scale the animal image
                ImageIcon animalIcon = new ImageIcon("img/" + selectedAnimal.getPieceName() + ".png");
                Image scaledAnimal = animalIcon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
                animalButtons[index].setIcon(new ImageIcon(scaledAnimal));
                animalButtons[index].setDisabledIcon(new ImageIcon(scaledAnimal));
                animalButtons[index].setEnabled(false);
                
                // Update main display image
                imageLabel.setIcon(new ImageIcon(scaledAnimal));
                
                if (selectionsMade == 1) {
                    player1Animal = selectedAnimal;
                    topLabel.setText("Player 2: Select an animal card");
                } else if (selectionsMade == 2) {
                    player2Animal = selectedAnimal;
                    determineFirstPlayer();
                }
            }
        }
    }

    private void determineFirstPlayer() {
        for (JButton button : animalButtons) {
            button.setEnabled(false);
        }

        String message;
        if (player1Animal.getStrength() > player2Animal.getStrength()) {
            message = "Player 1 (" + player1Animal.getPieceName() + ") goes first!";
        } else if (player2Animal.getStrength() > player1Animal.getStrength()) {
            message = "Player 2 (" + player2Animal.getPieceName() + ") goes first!";
        } else {
            message = "Equal strength! Randomly selecting first player...";
        }

        topLabel.setText(message);
        
        JButton continueButton = new JButton("START GAME");
        continueButton.setFont(new Font("Arial", Font.BOLD, 24));
        continueButton.addActionListener(e -> {
            this.dispose();
            new JungleKingBoard(player1Animal.getStrength() > player2Animal.getStrength() ? 1 : 2);
        });
        
        getContentPane().remove(1); // Remove the card panel
        add(continueButton, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }


}