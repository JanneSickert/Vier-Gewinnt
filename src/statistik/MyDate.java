package statistik;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Gibt Zeit und Datum zurück
 * @author Janne
 *
 */
public class MyDate {
	/**
	 * 
	 * @return Zeit und Datum im Format HH:mm:ss dd.MM.yyyy
	 */
	public String getTimePoint() {
		String time = getTime() + " " + getDate();
		return time;
	}

	private String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		Date currentTime = new Date();
		String d = formatter.format(currentTime);
		return d;
	}

	private String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String d = formatter.format(currentTime);
		return d;
	}
}