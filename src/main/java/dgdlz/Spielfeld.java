package dgdlz;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import dgdlz.akteure.Spieler;
import dgdlz.gegenstaende.Gegenstand;
import dgdlz.raeume.Raum;

public class Spielfeld {

	private static final String QUERY_RAEUME = """
			SELECT * FROM textadventure.spielobjekt
			WHERE spielobjekt_typ = 1
			""";
	private static final String QUERY_GEGENSTAENDE = """
			SELECT * FROM textadventure.spielobjekt
			WHERE spielobjekt_typ = 2
			""";
	private static Spielfeld instance;

	private Spielfeld() {
	}

	public static Spielfeld getInstance() {
		if (instance == null) {
			instance = new Spielfeld();
		}
		return instance;
	}

	private List<Raum> alleRaeume = new ArrayList<>();
	private List<Raum> aktuelleNachbarraeume = new ArrayList<>();
	private List<Gegenstand> alleGegenstaende = new ArrayList<>();

	// Gegenstände
	public void setAlleGegenstaende() {
		alleGegenstaende.clear();
		List<Gegenstand> gegenstaende = erzeugeGegenstand();
		for (Gegenstand g : gegenstaende) {
			alleGegenstaende.add(g);
		}
	}

	private static List<Gegenstand> erzeugeGegenstand() {
		Configuration con = new Configuration().configure().addAnnotatedClass(Gegenstand.class);
		StandardServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf = con.buildSessionFactory(reg);
		Session session = sf.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List<Gegenstand> gegestaende = session.createNativeQuery(QUERY_GEGENSTAENDE, Gegenstand.class).list();
			tx.commit();
			return gegestaende;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public List<Gegenstand> getAlleGegenstaende() {
		return alleGegenstaende;
	}

	// Räume
	public void setAlleRaeume() {
		alleRaeume.clear();
		List<Raum> raeume = erzeugeRaum();
		for (Raum r : raeume) {
			alleRaeume.add(r);
		}
	}

	private static List<Raum> erzeugeRaum() {
		Configuration con = new Configuration().configure().addAnnotatedClass(Raum.class);
		StandardServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf = con.buildSessionFactory(reg);
		Session session = sf.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List<Raum> raeume = session.createNativeQuery(QUERY_RAEUME, Raum.class).list();
			tx.commit();
			return raeume;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public List<Raum> getAlleRaeume() {
		return alleRaeume;
	}

	public List<Raum> getNachbarraeume() {
		return aktuelleNachbarraeume;
	}

	public void initSpielfeld() { // TODO
		setAlleRaeume();
		setAlleGegenstaende();
	}

	public Raum ermittleAufenthaltsraumSpieler(Spieler spieler) {
		for (Raum r : alleRaeume) {
			if (spieler.getPosition().equals(r.getPosition())) {
				return r;
			}
		}
		throw new IllegalArgumentException("Spieler muss in einem Raum sein.");
	}

	public List<Raum> ermittleDieNachbarraeume(Spieler spieler) {
		aktuelleNachbarraeume.clear();
		for (Raum r : alleRaeume) {
			if (r.getPosition().distance(spieler.getPosition()) == 1) {
				aktuelleNachbarraeume.add(r);
			}
		}
		return aktuelleNachbarraeume;
	}

	public Himmelsrichtung ermittleMoeglicheHimmelsrichtungen(Spieler spieler) {
		for (Raum r : aktuelleNachbarraeume) {
			if (r.getPosition().y == spieler.getPosition().y + 1) {
				return Himmelsrichtung.NORDEN;
			}
			if (r.getPosition().y == spieler.getPosition().y - 1) {
				return Himmelsrichtung.SUEDEN;
			}
			if (r.getPosition().x == spieler.getPosition().x + 1) {
				return Himmelsrichtung.OSTEN;
			}
			if (r.getPosition().x == spieler.getPosition().x - 1) {
				return Himmelsrichtung.WESTEN;
			}
		}
		throw new IllegalArgumentException(
				"Es gibt nur vier Himmelsrichtungen, in die eine Spielfigur bewegt werden kann.");
	}

}
