package statistik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import info.Do;
/**
 * Öffnet das Fenster das die bisherigen Spiele anzeigt
 * @author Janne
 *
 */
public class OpenStatistik {

	private JFrame frame = new JFrame();
	/**
	 * ArrayList mit den Seiten indem die Spiele stehen.
	 */
	private ArrayList<Page> list = new ArrayList<Page>();
	private Logistic importExport = new Logistic();
	private final int X = 200;
	private final int Y = 50;
	private int pageNr = 0;// Seite auf der gestarten wird.

	@Do(aufgabe = "Überträgt die Importiereten Daten auf das Panel der Page Class & "
			+ "Fügt Buttons zum wechseln der Seiten hinzu.")
	/**
	 * Überträgt die Importiereten Daten auf das Panel der Page Class Fügt Buttons
	 * zum wechseln der Seiten hinzu.
	 */
	public void build() {
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocation(600, 20);
		frame.setTitle("Statistik");
		frame.setSize(4 * X, 18 * Y);
		String[][] arr = importExport.inport();
		int nr = (int) (arr.length / 16) + 1;
		for (int i = 0; i < nr; i++) {
			list.add(new Page());
			for (int x = 0; x < 16; x++) {
				for (int y = 0; y < 4; y++) {
					if (x < arr.length) {
						list.get(0).add(new JLabel(edit(arr[x][y])));
					} else {
						list.get(0).add(new JLabel());
					}
				}
			}
			JButton vor = new JButton("<html><font size=\"+1\">Vor</font></html>");
			vor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (pageNr > 0) {
						pageNr--;
						changePage();
					}
				}
			});
			JButton zurueck = new JButton("<html><font size=\"+1\">Zurück</font></html>");
			zurueck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (pageNr < nr - 1) {
						pageNr++;
						changePage();
					}
				}
			});
			JButton menu = new JButton("<html><font size=\"+1\">Menu</font></html>");
			menu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					new settings.Auswahl();
				}
			});
			JButton ersteSeite = new JButton("<html><font size=\"+1\">erste Seite</font></html>");
			ersteSeite.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pageNr = nr - 1;
				}
			});
			list.get(i).add(vor);
			list.get(i).add(zurueck);
			list.get(i).add(ersteSeite);
			list.get(i).add(menu);
		}
		// Nun müssen die fertigen Panels den JFrame hinzugefügt werden.
		changePage();
		frame.setVisible(true);
	}

	private void changePage() {
		frame.add(list.get(pageNr));
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * 
	 * @param zu
	 *            formatierender String
	 * @return formatierter String
	 */
	private String edit(String str) {
		return "<html><font size=\"+1\">" + str + "</font></html>";
	}
}