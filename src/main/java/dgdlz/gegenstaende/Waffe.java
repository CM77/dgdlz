package dgdlz.gegenstaende;

public abstract class Waffe extends Gegenstand {

	private int maxSchadenspunkte;

	public int getSchadenspunkte() {
		return maxSchadenspunkte;
	}

	public void setSchadenspunkte(int schadenspunkte) {
		this.maxSchadenspunkte = schadenspunkte;
	}
}
