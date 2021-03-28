package statistik;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Zeichnet die �berschrift einer Seite anschlie�end k�nnen den Objekt weitere
 * komponenten hinzugef�gt werden.
 * 
 * @author Janne
 *
 */
@SuppressWarnings("serial")
public class Page extends JPanel {

	private JLabel[] head = new JLabel[4];

	private String edit(String str) {
		return "<html><b><font size=\"+2\">" + str + "</font></b></html>";
	}

	public Page() {
		setLayout(new GridLayout(18, 4));
		head[0] = new JLabel(edit("Spieler 1"));
		head[1] = new JLabel(edit("Spieler 2"));
		head[2] = new JLabel(edit("Gewinner"));
		head[3] = new JLabel(edit("Zeit / Datum"));
		add(head[0]);
		add(head[1]);
		add(head[2]);
		add(head[3]);
	}
}