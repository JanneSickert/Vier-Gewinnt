package settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import gewinn.Paths;
import statistik.OpenStatistik;

/**
 * Beschreibt das ausehen des Start Fensters in dem, zwischen 2 Spielern oder
 * gegen den Computer gespielt werden kann.
 * 
 * @author Janne
 * @author Dario
 */
public class Auswahl {

	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private final int X = 350;
	private final int Y = 250;
	private JButton twoPlayer, onePlayer, statistik;

	public Auswahl() {
		willkomen();
	}

	public void willkomen() {
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocation(600, 300);
		frame.setTitle("Vier gewinnt");
		frame.setSize(X * 2, 340);
		panel.setLayout(null);
		twoPlayer = new JButton();
		onePlayer = new JButton();
		statistik = new JButton("<html><font size=\"+2\">Statistik bisher gespielter Spiele</font></html>");
		statistik.setLocation(0, Y);
		statistik.setSize(2 * X, 50);
		twoPlayer.setSize(X, Y);
		onePlayer.setSize(X, Y);
		twoPlayer.setLocation(0, 0);
		onePlayer.setLocation(X, 0);
		twoPlayer.setIcon(new ImageIcon(Paths.spielerGegenSpieler));
		onePlayer.setIcon(new ImageIcon(Paths.gegenComputer));
		twoPlayer.addActionListener(new Opt());
		onePlayer.addActionListener(new Opt());
		statistik.addActionListener(new Opt());
		panel.add(statistik);
		panel.add(onePlayer);
		panel.add(twoPlayer);
		frame.add(panel);
		frame.setVisible(true);
	}

	private class Opt implements ActionListener {
		@SuppressWarnings("static-access")
		@Override
		// Aktionen zur Auswahl des Spielmoduses
		public void actionPerformed(ActionEvent nrOfBu) {
			if (nrOfBu.getSource() == onePlayer) {
				// Version gegen den Computer
				new android.Namenseingabe().def();
			} else if (nrOfBu.getSource() == twoPlayer) {
				// Version: Spieler gegen Spieler
				new Nameneingabe().def();
				frame.setVisible(false);
			} else if (nrOfBu.getSource() == statistik) {
				new OpenStatistik().build();
				frame.setVisible(false);
			}
		}
	}
}