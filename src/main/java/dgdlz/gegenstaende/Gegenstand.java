package dgdlz.gegenstaende;

import dgdlz.akteure.Spielobjekt;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Gegenstand extends Spielobjekt {

	@Override
	public String untersuchen() {
		return this.getBeschaffenheit();
	}

}
