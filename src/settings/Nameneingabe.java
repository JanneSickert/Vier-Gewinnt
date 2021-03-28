package settings;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import gewinn.VierGewinnt;
import info.Do;
/**
 * Namenseingabe für die 2 Spieler Version
 * @author Janne
 *
 */
public class Nameneingabe {

	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JLabel[] spieler = new JLabel[2];
	private JTextField[] tf = new JTextField[2];
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
		frame.setTitle("Vier gewinnt");
		frame.setSize(700, 340);
		panel.setLayout(null);
		spieler[0] = new JLabel("Name Spieler 1:");
		spieler[1] = new JLabel("Name Spieler 2:");
		spieler[0].setLocation(50, 50);
		spieler[0].setSize(X, Y);
		spieler[1].setLocation(50, 100);
		spieler[1].setSize(X, Y);
		tf[0] = new JTextField();
		tf[1] = new JTextField();
		final int fontSize = 25;
		Font font = tf[0].getFont();
		tf[0].setFont(new Font(font.getFontName(), font.getStyle(), fontSize));
		tf[1].setFont(new Font(font.getFontName(), font.getStyle(), fontSize));
		tf[0].setForeground(Color.RED);
		tf[1].setForeground(Color.BLUE);
		tf[0].setLocation(50 + X, 50);
		tf[1].setLocation(50 + X, 100);
		tf[0].setSize(X, Y);
		tf[1].setSize(X, Y);
		enter.setSize(X, Y);
		enter.setLocation(50 + X, 150);
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] names = { tf[0].getText(), tf[1].getText() };
				frame.setVisible(false);
				new VierGewinnt(names[0], names[1]);
			}
		});
		panel.add(enter);
		panel.add(spieler[0]);
		panel.add(spieler[1]);
		panel.add(tf[0]);
		panel.add(tf[1]);
		frame.add(panel);
		frame.setVisible(true);
	}
}