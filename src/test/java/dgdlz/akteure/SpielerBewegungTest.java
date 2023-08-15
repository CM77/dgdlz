package dgdlz.akteure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dgdlz.IllegalerRaumException;
import dgdlz.Spielfeld;
import dgdlz.raeume.Raum;

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
	public void test_Position_Spieler__Aufenthaltsraum() {
		// Given:
		spielerActual.setPosition(new Point(0, 0));
		Raum expectedRaum = new Raum();
		expectedRaum.setPosition(new Point(0, 0));
		Point expected = expectedRaum.getPosition();

		// When:
		Point actual = spielfeld.ermittleAufenthaltsraumSpieler(spielerActual).getPosition();

		// Then:
		assertEquals(expected, actual);
	}

	@Test
	public void test_Position_Spieler__Nachbarraeume() {
		// Given:
		spielerActual.setPosition(new Point(0, 0));

		// When:
		List<Raum> actual = spielfeld.ermittleDieNachbarraeume(spielerActual);

		// Then:
		assertTrue(actual.size() == 2);
	}

	@Test
	public void test_Position_Spieler__Nachbarraeume2() {
		// Given:
		spielerActual.setPosition(new Point(0, 3));

		// When:
		List<Raum> actual = spielfeld.ermittleDieNachbarraeume(spielerActual);

		// Then:
		assertTrue(actual.size() == 1);
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

	@Test
	public void test_Position_Spieler__unmoeglicher_Spielzug() {
		// Given:
		spielerActual.setPosition(new Point(0, 0));

		// When:

		// Then:
		assertThrowsExactly(IllegalerRaumException.class, () -> {

		});
	}

}
