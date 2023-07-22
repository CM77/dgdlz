package dgdlz;

public enum Himmelsrichtung {
	NORDEN("Norden"), SUEDEN("Süden"), OSTEN("Osten"), WESTEN("Westen");

	String richtungName;

	Himmelsrichtung(String name) {
		richtungName = name;
	}

	@Override
	public String toString() {
		return richtungName;
	}
}
