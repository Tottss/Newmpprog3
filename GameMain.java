import javax.swing.*;
import java.awt.*;

public class GameMain {
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setLayout(new GridBagLayout());
		frame.setMinimumSize(new Dimension(1000, 1000));
		frame.setLocationRelativeTo(null);
		
		GameView gameView = new GameView();
		frame.add(gameView);
		
		frame.setVisible(true);
	}
}
