package dgdlz.akteure;

public final class Spieler extends Spielfigur implements Bewegbar {

	@Override
	public String nachNordenBewegen() {
		this.setPositionY(Math.max(0, this.getPositionY() + 1));
		return "Du gehst nach Norden.";
	}

	@Override
	public String nachSuedenBewegen() {
		this.setPositionY(Math.min(4, this.getPositionY() - 1));
		return "Du gehst nach Süden.";
	}

	@Override
	public String nachOstenBewegen() {
		this.setPositionX(Math.max(0, this.getPositionX() + 1));
		return "Du gehst nach Osten.";
	}

	@Override
	public String nachWestenBewegen() {
		this.setPositionX(Math.min(4, this.getPositionX() - 1));
		return "Du gehst nach Westen.";
	}

	@Override
	public String untersuchen() {
		return "Du schaust dich selber an, aber irgendwie siehst du nur deine Hände, deine Hose und deine Schuhe. Ein Spiegel verrät vielleicht mehr über dein (hoffentlich) Top-Model-haftes Aussehen.";
	}
}
