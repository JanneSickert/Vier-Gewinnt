package statistik;

import info.Do;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import gewinn.Paths;

/**
 * Speichert und liest Daten über frühere Spiele von der Festplatte
 * 
 * @author Janne
 *
 */
public class Logistic {

	private String pathNr = Paths.nr;

	@Do(aufgabe = "erstellt eine Neue Datei in der das Spielergebnis gespeichert wird."
			+ " als 4 Eintrag wird die Aktuelle Zeit und das Datum gespeichert.", parameter = { "Name von Spieler 1",
					"Name von Spieler 2", "Spielergebnis:" + " Spieler 1 oder Spieler 2 oder Unentschieden" })
	/**
	 * <b>erstellt eine Neue Datei in der das Spielergebnis gespeichert wird.</b>
	 * 
	 * @param Name
	 *            spieler 1
	 * @param Name
	 *            spieler 2
	 * @param Spielergebnis
	 */
	public void export(String name1, String name2, String ergebnis) {
		String dataName = "history/game_" + getNr() + ".txt";
		File f = new File(dataName);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Datei " + dataName + " konnte nicht erstellt werden");
		}
		if (f.exists()) {
			// datei wurde erfolgreich erstellt
			try {
				FileWriter fwf = new FileWriter(f, false);
				fwf.write(name1 + "?" + name2 + "?" + ergebnis + "?" + new MyDate().getTimePoint() + "?");
				fwf.close();
			} catch (IOException e) {
				// ERRORS
				e.printStackTrace();
				System.out.println("Es konnte nicht in die Datei geschrieben werden: " + dataName);
			}
		} else {
			System.out.println("Datei wurde nicht gefunden:" + dataName);
		}
	}

	@Do(aufgabe = "Zählt die Nummer der Spiele in der 'nr' datei um eins nach oben.")
	/**
	 * Zählt die Nummer der Spiele in der 'nr' datei um eins nach oben.
	 */
	public void countGame() {
		int nr = getNr() + 1;
		File ff = new File(pathNr);
		if (ff.exists()) {
			// datei existiert
			try {
				FileWriter fwf = new FileWriter(ff, false);
				fwf.write("" + nr);
				fwf.close();
			} catch (IOException e) {
				// ERRORS
				e.printStackTrace();
				System.out.println("Es konnte nicht in die Datei geschrieben werden: " + pathNr);
			}
		} else {
			System.out.println("Datei wurde nicht gefunden:" + pathNr);
		}
	}

	/**
	 * 
	 * @return die anzahl der in der statistik gespeicherten Spiele
	 */
	private int getNr() {
		String zeile = null;
		FileReader fr = null;
		try {
			fr = new FileReader(pathNr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FEHLER beim öffnen der Datei");
		}
		BufferedReader br = new BufferedReader(fr);
		try {
			zeile = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FEHLER beim Lesen der Datei");
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FEHLER bei schließen der Datei");
		}
		int res = Integer.parseInt(zeile);
		return res;
	}

	@Do(aufgabe = "Liefert die Werte für die statistik", back = "{name1, name2, ergebnis des Spiels}"
			+ " von diesen Objekten werden soviele zurückgegeben wie Spiele gespielt wurden.")
	/**
	 * Liefert die Werte für die statistik
	 * 
	 * @return von diesen Objekten werden soviele zurückgegeben wie Spiele gespielt
	 *         wurden.
	 */
	public String[][] inport() {
		int nog = getNr(); // nog = number Of Games
		if (nog > 0) {
			String[][] arr = new String[nog][4];
			for (int i = 1; i <= nog; i++) {
				String zeile = null;
				FileReader fr = null;
				try {
					fr = new FileReader("history/game_" + i + ".txt");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("FEHLER bei importieren von: " + " history/game_" + i + ".txt" + " BLOCK 1");
				}
				BufferedReader br = new BufferedReader(fr);
				try {
					zeile = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("FEHLER beim importieren der Zeile BLOCK 2 Pfad: " + "history/game_" + i);
				}
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("FEHLER bei schließen der Datei" + "history/game_" + i);
				}
				// Nun steht in zeile der Text Spieler1?Spieler2?Ergebnis?Zeitpunkt der sotiert
				// werden muss.
				ArrayList<String> s1 = new ArrayList<String>();
				ArrayList<String> s2 = new ArrayList<String>();
				ArrayList<String> s3 = new ArrayList<String>();
				ArrayList<String> s4 = new ArrayList<String>();
				int index = 0;
				while (!(zeile.charAt(index) == '?')) {
					String str1 = "" + new String(new char[] { zeile.charAt(index) });
					s1.add(str1);
					index++;
				}
				index++;
				while (!(zeile.charAt(index) == '?')) {
					String str2 = "" + new String(new char[] { zeile.charAt(index) });
					s2.add(str2);
					index++;
				}
				index++;
				while (!(zeile.charAt(index) == '?')) {
					String str3 = "" + new String(new char[] { zeile.charAt(index) });
					s3.add(str3);
					index++;
				}
				index++;
				while (!(zeile.charAt(index) == '?')) {
					String str4 = "" + new String(new char[] { zeile.charAt(index) });
					s4.add(str4);
					index++;
				}
				arr[i - 1][0] = ArrayListToString(s1);
				arr[i - 1][1] = ArrayListToString(s2);
				arr[i - 1][2] = ArrayListToString(s3);
				arr[i - 1][3] = ArrayListToString(s4);
			}
			return arr;
		} else {
			// Wird zurückgegeben wenn noch keine Spiele gespielt wurden.
			String[][] arr = { { "kein Eintrag", "kein Eintrag", "kein Eintrag", "kein Eintrag" } };
			return arr;
		}
	}

	private String ArrayListToString(ArrayList<String> list) {
		String res = "";
		for (int i = 0; i < list.size(); i++) {
			res = res + list.get(i);
		}
		return res;
	}
}