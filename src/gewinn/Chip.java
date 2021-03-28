package gewinn;

import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * Gibt einen einzelnen Spielstein funktsionen
 * @author Janne
 * @author Dario
 */
public class Chip extends JButton {

	private final int FIELDS = 7;
	private final int XY = 1050;
	private final int CHIP = XY / FIELDS;
	private Typs typ;

	public Chip() {
		setIcon(new ImageIcon(Paths.keiner));
		setSize(CHIP, CHIP);
		typ = Typs.LEHR;
	}

	public void setColor(Typs val) {
		// Setzt in einem Feld eine Farbe
		switch (val) {
		case BLAU:
			setIcon(new ImageIcon(Paths.blauer));
			typ = Typs.BLAU;
			break;
		case ROT:
			setIcon(new ImageIcon(Paths.roter));
			typ = Typs.ROT;
			break;
		default:
			System.out.println("LEHR ist kein gültiger Parameter");
		}
	}

	public Typs getColor() {
		// gibt die acktuelle Farbe zurück
		return typ;
	}
}