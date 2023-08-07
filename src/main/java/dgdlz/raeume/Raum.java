package dgdlz.raeume;

import dgdlz.akteure.Spielobjekt;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class Raum extends Spielobjekt {

	@Override
	public String untersuchen() {
		return this.getBeschaffenheit();
	}

}
