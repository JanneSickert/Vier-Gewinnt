package android;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import gewinn.Chip;
import gewinn.Typs;
import info.Do;
import statistik.Logistic;

/**
 * 
 * Mit zug(x) k?nnen wir einen Stein setzen mit getField kann man sich die
 * Spielfeld ArrayList zur?ckgeben lassen. der Computer hat Rot wenn zuege=0 ist
 * beginnt er.
 * 
 * <b>Strategie des Computerspielers</b>
 * 
 * 1.) Die meisten m?glichkeiten hat man in der Mitte
 *     deswegen versucht der Computer als erstes die Spalte in der Mitte
 *     voll zu bekommen insofern ein gewinn noch m?glich ist.
 *   
 *   
 * 2.) Wenn der Gegnere drei Steine in einer Rheie,
 *   Spalte oder Zeile hat versucht der Computer dies zu verhindern.
 *   Dabei schaut sich der Computer alle m?glichen drei Steine
 *   kombinatsionen an.
 *   
 * 3.) Nicht die Zelle f?llen unter einen Chip der das Spiel beeendet.
 * 
 * @author Janne
 * @author Dario
 */
public class Computer {

	private final int FIELDS = 7; // Anzhal der felder in einer Zeile oder
									// Spalte
	private final int XY = 1050; // Breite und h?he
	private ArrayList<Chip> list; // Liste mit den Feldern die Operationen
									// enthalten
	private JFrame frame;
	private JPanel panel;
	private int zuege = getZeroOrOne();
	private Console c = new Console();
	private String spieler1, spieler2; // Namen der spielenden Spieler
	private String currentPlayer;
	private int zeilen = 4; // Volle Zeiten in der Konsole
	private Timer timer;
	private int currentTime = 20;
	private boolean[] arr = new boolean[FIELDS]; // Speichert die als voll
													// registriereten Spalten.
	private Logistic log = new Logistic();

	/**
	 * Speichert bereits belegte felder beim Gegner ab.
	 */
	private boolean[][] steineGegner = new boolean[FIELDS][FIELDS];
	/**
	 * Speichert bereits belegte felder beim Computer ab.
	 */
	private boolean[][] steineComputer = new boolean[FIELDS][FIELDS];

	private void fuelleArray() {
		for (int x = 0; x < FIELDS; x++) {
			for (int y = 0; y < FIELDS; y++) {
				steineGegner[x][y] = false;
			}
		}
		for (int x = 0; x < FIELDS; x++) {
			for (int y = 0; y < FIELDS; y++) {
				steineComputer[x][y] = false;
			}
		}
	}

	public Computer(String spieler1) {
		this.spieler1 = spieler1;
		this.spieler2 = "Android";
		frame = new JFrame();
		panel = new JPanel();
		c.open();
		windows();
		createChips();
		build();
		initTimer();
		for (int i = 0; i < FIELDS; i++) {
			arr[i] = true; // Beschreibt das keine der Spalten voll ist.
		}
		setPlayer();
	}

	private void setPlayer() {
		if (zuege == 0) {
			currentPlayer = this.spieler1;
		} else {
			currentPlayer = this.spieler2;
			computer();
		}
	}

	@Do(aufgabe = "Entscheidet per Zufall ob Rot oder Blau beginnt", back = "0 oder 1")
	private int getZeroOrOne() {
		int nr = (int) Math.round(Math.random());
		return nr;
	}

	@Do(aufgabe = "Beschreibt den Timer und gibt die Zeit aus bis der andere spielen kann.")
	private void initTimer() {
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentTime--;
				if (currentTime == 15) {
					schreiber("Du hast noch 15sek");
				}
				if (currentTime == 10) {
					schreiber("Du hast noch 10sek");
				}
				if (currentTime == 5) {
					schreiber("Du hast noch 5sek");
				}
				if (currentTime == 0) {
					schreiber("Deine Zeit ist abgelaufen");
					if (currentPlayer.equals(spieler1)) {
						schreiber(spieler2 + " ist dran.");
					} else {
						schreiber(spieler1 + " ist dran.");
					}
					zuege++;
					currentTime = 20;
					timer.restart();
				}
			}
		});
		timer.start();
	}

	private class Console {

		public JFrame frame = new JFrame();
		public JPanel panel = new JPanel();
		public JTextArea con = new JTextArea();
		public Font font = con.getFont();
		public int SUB = 300, fontSize = 20, x[] = { 50, 50 }; // SUB = gr??e
		// der Konsole,
		// fontSize =
		// Schriftgr??e,
		// x = positsion
		public boolean visibility = true;

		@Do(aufgabe = "?ffnet die Konsole")
		public void open() {
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setResizable(false);
			frame.setLocation(x[0], x[1]);
			frame.setTitle("Java Konsole");
			frame.setSize(SUB, SUB);
			panel.setLayout(null);
			con.setSize(SUB, SUB);
			con.setLocation(5, 5);
			con.setFont(new Font(font.getFontName(), font.getStyle(), fontSize));
			String spieler = "";
			if (zuege % 2 == 0) {
				spieler = spieler1;
			} else {
				spieler = spieler2;
			}
			con.setText("Spiel gestartet \n Jeder Spielzug darf h?chstens \n 20 Sekunden dauern. \n" + spieler
					+ " beginnt das Spiel \n");
			panel.add(con);
			frame.add(panel);
			frame.setVisible(visibility);
		}
	}

	@Do(aufgabe = "erstellt die Felder im Spielfeld")
	private void createChips() {
		list = new ArrayList<Chip>();
		for (int i = 0; i < FIELDS * FIELDS; i++) {
			Chip chip = new Chip();
			chip.addActionListener(new Handler());
			list.add(chip);
		}
	}

	@Do(aufgabe = "Setzt die Felder auf das Panel")
	private void build() {
		for (int inn = 0; inn < FIELDS * FIELDS; inn++) {
			panel.add(list.get(inn));
		}
		frame.add(panel);
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();
	}

	@Do(aufgabe = "Definiert das JFrame")
	private void windows() {
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocation(400, 5);
		frame.setTitle("Vier Gewinnt");
		frame.setSize(XY, XY);
		panel.setLayout(new GridLayout(FIELDS, FIELDS));
	}

	@Do(aufgabe = "Schreibt in die Konsole", parameter = { "Der Text der in die Konsole geschrieben werden soll." })
	private void schreiber(String neuerText) {
		if (zeilen > FIELDS + 2) {
			c.con.setText("");
			zeilen = 0;
			c.con.setText(neuerText + "\n");
		} else {
			c.con.setText(c.con.getText() + neuerText + "\n");
		}
		zeilen += 1;
	}

	@Do(aufgabe = "Setzt einen Stein in die im Parameter ?bergebene Spalte 0 ist ganz links.", parameter = {
			"Die Spalte in der, der Stein gesetzt werden soll." })
	private void zug(int x) {
		if (zuege % 2 == 0) {
			for (int i = FIELDS - 1; i >= 0; i--) {
				int point = (FIELDS * i) + x;
				if (list.get(point).getColor() == Typs.LEHR) {
					list.get(point).setColor(Typs.ROT);
					i = 0;
				}
			}
			currentPlayer = spieler2;
			schreiber(spieler2 + " ist dran.");
		} else {
			for (int i = FIELDS - 1; i >= 0; i--) {
				int point = (FIELDS * i) + x;
				if (list.get(point).getColor() == Typs.LEHR) {
					list.get(point).setColor(Typs.BLAU);
					i = 0;
				}
			}
			currentPlayer = spieler1;
			schreiber(spieler1 + " ist dran.");
		}
		zuege += 1;
		currentTime = 20;
		timer.restart();
		testWinner();
		testZugMoeglich();
		testUnentschieden();
	}

	private class Handler implements ActionListener {
		@Override
		@Do(aufgabe = "Erfasst welcher Button gedr?ckt wurde.")
		public void actionPerformed(ActionEvent arg) {
			if (arg.getSource() == list.get(0) || arg.getSource() == list.get(FIELDS)
					|| arg.getSource() == list.get(FIELDS * 2) || arg.getSource() == list.get(FIELDS * 3)
					|| arg.getSource() == list.get(FIELDS * 4) || arg.getSource() == list.get(FIELDS * 5)
					|| arg.getSource() == list.get(FIELDS * 6)) {
				// alle felder in X = 1
				zug(0);
				for (int i = 0; i < FIELDS; i++) {
					if (steineGegner[0][i] == false || steineComputer[0][i] == false) {
						steineGegner[0][i] = true;
						i = 8;
					}
				}
			} else if (arg.getSource() == list.get(1) || arg.getSource() == list.get(FIELDS + 1)
					|| arg.getSource() == list.get((FIELDS * 2) + 1) || arg.getSource() == list.get((FIELDS * 3) + 1)
					|| arg.getSource() == list.get((FIELDS * 4) + 1) || arg.getSource() == list.get((FIELDS * 5) + 1)
					|| arg.getSource() == list.get((FIELDS * 6) + 1)) {
				// alle felder in x = 2
				zug(1);
				for (int i = 0; i < FIELDS; i++) {
					if (steineGegner[1][i] == false || steineComputer[0][i] == false) {
						steineGegner[1][i] = true;
						i = 8;
					}
				}
			} else if (arg.getSource() == list.get(2) || arg.getSource() == list.get(FIELDS + 2)
					|| arg.getSource() == list.get((FIELDS * 2) + 2) || arg.getSource() == list.get((FIELDS * 3) + 2)
					|| arg.getSource() == list.get((FIELDS * 4) + 2) || arg.getSource() == list.get((FIELDS * 5) + 2)
					|| arg.getSource() == list.get((FIELDS * 6) + 2)) {
				// alle felder in x = 3
				zug(2);
				for (int i = 0; i < FIELDS; i++) {
					if (steineGegner[2][i] == false || steineComputer[0][i] == false) {
						steineGegner[2][i] = true;
						i = 8;
					}
				}
			} else if (arg.getSource() == list.get(3) || arg.getSource() == list.get(FIELDS + 3)
					|| arg.getSource() == list.get((FIELDS * 2) + 3) || arg.getSource() == list.get((FIELDS * 3) + 3)
					|| arg.getSource() == list.get((FIELDS * 4) + 3) || arg.getSource() == list.get((FIELDS * 5) + 3)
					|| arg.getSource() == list.get((FIELDS * 6) + 3)) {
				// alle felder in x = 4
				zug(3);
				for (int i = 0; i < FIELDS; i++) {
					if (steineGegner[3][i] == false || steineComputer[0][i] == false) {
						steineGegner[3][i] = true;
						i = 8;
					}
				}
			} else if (arg.getSource() == list.get(4) || arg.getSource() == list.get(FIELDS + 4)
					|| arg.getSource() == list.get((FIELDS * 2) + 4) || arg.getSource() == list.get((FIELDS * 3) + 4)
					|| arg.getSource() == list.get((FIELDS * 4) + 4) || arg.getSource() == list.get((FIELDS * 5) + 4)
					|| arg.getSource() == list.get((FIELDS * 6) + 4)) {
				// alle felder in x = 5
				zug(4);
				for (int i = 0; i < FIELDS; i++) {
					if (steineGegner[4][i] == false || steineComputer[0][i] == false) {
						steineGegner[4][i] = true;
						i = 8;
					}
				}
			} else if (arg.getSource() == list.get(5) || arg.getSource() == list.get(FIELDS + 5)
					|| arg.getSource() == list.get((FIELDS * 2) + 5) || arg.getSource() == list.get((FIELDS * 3) + 5)
					|| arg.getSource() == list.get((FIELDS * 4) + 5) || arg.getSource() == list.get((FIELDS * 5) + 5)
					|| arg.getSource() == list.get((FIELDS * 6) + 5)) {
				// alle felder in x = 6
				zug(5);
				for (int i = 0; i < FIELDS; i++) {
					if (steineGegner[5][i] == false || steineComputer[0][i] == false) {
						steineGegner[5][i] = true;
						i = 8;
					}
				}
			} else if (arg.getSource() == list.get(6) || arg.getSource() == list.get(FIELDS + 6)
					|| arg.getSource() == list.get((FIELDS * 2) + 6) || arg.getSource() == list.get((FIELDS * 3) + 6)
					|| arg.getSource() == list.get((FIELDS * 4) + 6) || arg.getSource() == list.get((FIELDS * 5) + 6)
					|| arg.getSource() == list.get((FIELDS * 6) + 6)) {
				// alle felder in x = 7
				zug(6);
				for (int i = 0; i < FIELDS; i++) {
					if (steineGegner[6][i] == false || steineComputer[0][i] == false) {
						steineGegner[6][i] = true;
						i = 8;
					}
				}
			}
			computer();
		}
	}

	private int countCop = 0;

	private void computer() {
		switch (countCop) {
		case 0:
			zug(3);
			countCop++;
			break;
		case 1:
			zug(3);
			countCop++;
			break;
		case 2:
			zug(3);
			countCop++;
			break;
		case 3:
			if (steineGegner[3][3] == false || steineComputer[3][3] == false) {
				zug(3);
			} else {
				zug(4);
			}
			countCop++;
			break;
		case 4:
			zug(3);
			countCop++;
			break;
		default:
			break;
		}
	}

	@Do(aufgabe = "?berpr?ft ob jemand gewonnen hat." + " Dabei werden alle m?glichen gewinnstellungen ?berpr?ft.")
	private void testWinner() {
		if (test(Typs.ROT)) {
			// Spieler1 hat gewonnen
			timer.stop();
			schreiber(spieler1 + " hat gewonnen!");
			log.countGame();
			log.export(spieler1, spieler2, spieler1);
			JOptionPane.showMessageDialog(null, spieler1 + " hat gewonnen!", "Spiel Gewonnen",
					JOptionPane.OK_CANCEL_OPTION);
			frame.setVisible(false);
			c.frame.setVisible(false);
			new settings.Auswahl();
		}
		if (test(Typs.BLAU)) {
			// Spieler2 hat gewonnen
			timer.stop();
			schreiber(spieler2 + " hat gewonnen!");
			log.countGame();
			log.export(spieler1, spieler2, spieler2);
			JOptionPane.showMessageDialog(null, spieler2 + " hat gewonnen!", "Spiel Gewonnen",
					JOptionPane.OK_CANCEL_OPTION);
			frame.setVisible(false);
			c.frame.setVisible(false);
			new settings.Auswahl();
		}
	}

	private boolean test(Typs par) {
		boolean ret = false;
		if (testHorizontal(par) || testVertikal(par) || testDiagonal(par)) {
			ret = true;
		}
		return ret;
	}

	private boolean testHorizontal(Typs typ) {
		boolean win = false;
		for (int y = 0; y < FIELDS * FIELDS; y += FIELDS) {
			for (int i = 0; i < FIELDS - 3; i++) {
				if (list.get(y + i).getColor() == typ && list.get(y + i + 1).getColor() == typ
						&& list.get(y + i + 2).getColor() == typ && list.get(y + i + 3).getColor() == typ) {
					win = true;
				}
			}
		}
		return win;
	}

	private boolean testVertikal(Typs typ) {
		boolean win = false;
		for (int i = 0; i < FIELDS; i++) {
			for (int y = 0; y < FIELDS * FIELDS - (FIELDS * 3); y += FIELDS) {
				if (list.get(i + y).getColor() == typ && list.get(i + y + FIELDS).getColor() == typ
						&& list.get(i + y + (2 * FIELDS)).getColor() == typ
						&& list.get(i + y + (3 * FIELDS)).getColor() == typ) {
					win = true;
				}
			}
		}
		return win;
	}

	private boolean testDiagonal(Typs typ) {
		boolean win[] = { false, false };
		for (int i = 0; i < (FIELDS * FIELDS - (3 * FIELDS)); i += FIELDS) {
			for (int j = 0; j < FIELDS - 3; j++) {
				int index = i + j;
				if (list.get(index).getColor() == typ && list.get(index + FIELDS + 1).getColor() == typ
						&& list.get(index + (FIELDS * 2) + 2).getColor() == typ
						&& list.get(index + (FIELDS * 3) + 3).getColor() == typ) {
					win[0] = true;
				}
			}
		}
		for (int i = 0; i < (FIELDS * FIELDS - (3 * FIELDS)); i += FIELDS) {
			for (int j = 3; j < FIELDS; j++) {
				int index = i + j;
				if (list.get(index).getColor() == typ && list.get(index + FIELDS - 1).getColor() == typ
						&& list.get(index + (FIELDS * 2) - 2).getColor() == typ
						&& list.get(index + (FIELDS * 3) - 3).getColor() == typ) {
					win[1] = true;
				}
			}
		}
		return (win[0] || win[1]);
	}

	@Do(aufgabe = "Testet ob die Spalte voll ist.")
	private void testZugMoeglich() {
		for (int i = 0; i < FIELDS; i++) {
			if (arr[i]) {
				boolean[] control = new boolean[FIELDS];
				for (int j = 0; j < FIELDS; j++) {
					control[j] = (list.get((j * FIELDS) + i).getColor() == Typs.ROT
							|| list.get((j * FIELDS) + i).getColor() == Typs.BLAU);
				}
				if (control[0] && control[1] && control[2] && control[3] && control[4] && control[5] && control[6]) {
					schreiber("Ung?ltiger Zug:");
					schreiber("Die Spalte ist voll");
					zuege--;
					arr[i] = false;
				}
			}
		}
	}

	@Do(aufgabe = "Wenn alle Felder voll sind ist das Spiel Unentschieden.")
	private void testUnentschieden() {
		if (zuege == (FIELDS * FIELDS)) {
			timer.stop();
			schreiber("Unentschieden!");
			log.countGame();
			log.export(spieler1, spieler2, "Unentschieden");
			JOptionPane.showMessageDialog(null, "Da es Unentschieden steht m?chtest du sicher nochmal spielen.",
					"Unentschieden", JOptionPane.OK_CANCEL_OPTION);
			frame.setVisible(false);
			c.frame.setVisible(false);
			new settings.Auswahl();
		}
	}
}