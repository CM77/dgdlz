package dgdlz.akteure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dgdlz.Spielfeld;

public class SpielerBewegungTest {

	// TODO fix Tests
	Spielfeld spielfeld = Spielfeld.getInstance();
	Spieler spielerActual = new Spieler();

	@BeforeEach
	public void init() {
		spielfeld.getAlleRaeume().clear();
		spielfeld.getNachbarraeume().clear();
		spielfeld.initSpielfeld();
	}

	@Test
	public void test_Position_Spieler__ein_Spielzug_nach_Norden() {
		// Given:
		spielerActual.setPosition(new Point(0, 0));

		// When:
		spielerActual.nachNordenBewegen();

		// Then:
		assertEquals(spielerActual.getPosition(), new Point(0, 1));
	}

	@Test
	public void test_Position_Spieler__ein_Spielzug_nach_Westen() {
		// Given:
		spielerActual.setPosition(new Point(1, 0));

		// When:
		spielerActual.nachWestenBewegen();

		// Then:
		assertEquals(spielerActual.getPosition(), new Point(0, 0));
	}

	@Test
	public void test_Position_Spieler__ein_Spielzug_nach_Sueden() {
		// Given:
		spielerActual.setPosition(new Point(0, 2));

		// When:
		spielerActual.nachSuedenBewegen();

		// Then:
		assertEquals(spielerActual.getPosition(), new Point(0, 1));
	}

	@Test
	public void test_Position_Spieler__ein_Spielzug_nach_Osten() {
		// Given:
		spielerActual.setPosition(new Point(0, 0));

		// When:
		spielerActual.nachOstenBewegen();

		// Then:
		assertEquals(spielerActual.getPosition(), new Point(1, 0));
	}

}
