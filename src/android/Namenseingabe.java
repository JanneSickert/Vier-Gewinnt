package android;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import info.Do;

/**
 * Die Namenseingabe für die Spieler gegen Computer version
 * 
 * @author Janne
 * @author Dario
 * 
 */
public class Namenseingabe {

	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JLabel spieler = new JLabel();
	private JTextField tf = new JTextField();
	private JButton enter = new JButton("Namen & AGB'S Betätigen");
	private final int X = 200;
	private final int Y = 50;

	@Do(aufgabe = "Beschreibt das Eingabefeld für Namen und den Button zum Starten der Partie")
	public void def() {
		// Beschreibt das Eingabefeld für Namen und den Button zum Starten der
		// Partie
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocation(600, 300);
		frame.setTitle("Namenseingabe");
		frame.setSize(700, 340);
		panel.setLayout(null);
		spieler = new JLabel("Dein Name: ");
		spieler.setLocation(50, 50);
		spieler.setSize(X, Y);
		tf = new JTextField();
		final int fontSize = 25;
		Font font = tf.getFont();
		tf.setFont(new Font(font.getFontName(), font.getStyle(), fontSize));
		tf.setForeground(Color.RED);
		tf.setLocation(50 + X, 50);
		tf.setSize(X, Y);
		enter.setSize(X, Y);
		enter.setLocation(50 + X, 150);
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Computer(tf.getText());
			}
		});
		panel.add(enter);
		panel.add(spieler);
		panel.add(tf);
		frame.add(panel);
		frame.setVisible(true);
	}
}