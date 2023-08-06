package dgdlz.akteure;

import java.awt.Point;

import dgdlz.gegenstaende.Mitnehmbar;
import dgdlz.gegenstaende.Untersuchbar;

public abstract class Spielobjekt implements Untersuchbar, Mitnehmbar {

	protected Point position;
	protected String name;
	private int gewicht;
	private boolean mitgenommen = false;

	public int getGewicht() {
		return gewicht;
	}

	public void setGewicht(int gewicht) {
		this.gewicht = gewicht;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String mitnehmen() {
		if (gewicht <= 300) {
			if (mitgenommen) {
				return name + " liegt bereits in deinem Rucksack!";
			} else {
				mitgenommen = true;
				return name + " wird von dir in den Rucksack gepackt.";
			}
		} else {
			return "Das ist zu schwer!";
		}
	}
}