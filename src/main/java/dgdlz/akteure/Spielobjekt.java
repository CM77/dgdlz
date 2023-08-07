package dgdlz.akteure;

import static java.util.Objects.requireNonNull;

import java.awt.Point;

import javax.annotation.Nullable;

import dgdlz.gegenstaende.Mitnehmbar;
import dgdlz.gegenstaende.Untersuchbar;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity(name = "spielobjekt")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "spielobjekt_typ", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Spielobjekt implements Untersuchbar, Mitnehmbar {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", updatable = false, nullable = false)
	protected Long id;

	@Column(name = "POS_X", nullable = false)
	private int positionX;

	@Column(name = "POS_Y", nullable = false)
	private int positionY;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "GEWICHT")
	private @Nullable Integer gewicht;

	@Column(name = "MITGENOMMEN", nullable = false)
	private @Nullable Boolean mitgenommen;

	@Column(name = "BESCHAFFENHEIT")
	private @Nullable String beschaffenheit;

	public Spielobjekt() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public Point getPosition() {
		return new Point(positionX, positionY);
	}

	public void setPosition(Point position) {
		this.positionX = position.x;
		this.positionY = position.y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = requireNonNull(name, "name");
	}

	public @Nullable Integer getGewicht() {
		return gewicht;
	}

	public void setGewicht(@Nullable Integer gewicht) {
		this.gewicht = gewicht;
	}

	public @Nullable Boolean isMitgenommen() {
		return mitgenommen;
	}

	public void setMitgenommen(@Nullable Boolean mitgenommen) {
		this.mitgenommen = mitgenommen;
	}

	public @Nullable String getBeschaffenheit() {
		return beschaffenheit;
	}

	public void setBeschaffenheit(@Nullable String beschaffenheit) {
		this.beschaffenheit = beschaffenheit;
	}

	@Override
	public String mitnehmen() {
		if (mitgenommen) {
			return this.name + " liegt bereits in deinem Rucksack!";
		} else {
			mitgenommen = true;
			return this.name + " wird von dir in den Rucksack gepackt.";
		}
	}

	@Override
	public String toString() {
		return "Spielobjekt [id=" + id + ", positionX=" + positionX + ", positionY=" + positionY + ", name=" + name
				+ ", gewicht=" + gewicht + ", mitgenommen=" + mitgenommen + ", beschaffenheit=" + beschaffenheit + "]";
	}

}