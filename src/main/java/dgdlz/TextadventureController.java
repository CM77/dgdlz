package dgdlz;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dgdlz.akteure.Spieler;
import dgdlz.gegenstaende.Gegenstand;
import dgdlz.raeume.Raum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TextadventureController implements Initializable {

	private static final String FXML_FILE = "Textadventure.fxml";

	public static TextadventureController load() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(TextadventureController.class.getResource(FXML_FILE));
		loader.load();
		return loader.getController();
	}

	@FXML
	private Parent root;
	@FXML
	private MenuItem spielBeenden;
	@FXML
	private VBox raumAktionenVb;
	@FXML
	private VBox gegenstandAktionenVb;
	@FXML
	private Button raumButton;
	@FXML
	private Button gegenstandButton;
	@FXML
	private Button rucksackButton;
	@FXML
	private TextArea textausgabeTa; // TODO TextFlow für variable Schriftgröße etc?
									// www.tutorialspoint.com/javafx/layout_panes_textflow.htm
	@FXML
	private ImageView roomIv;
	@FXML
	private TextField aufenthaltsraumTf;

	private Button erkundungsButton = new Button();
	private List<Button> listeMitRaumAktionsButtons = new ArrayList<>(Arrays.asList());
	private List<Button> listeMitGegenstandsAktionsButtons = new ArrayList<>(Arrays.asList());
	private String currentRoom;

	Spieler spieler = new Spieler();
	Spielfeld spielfeld = Spielfeld.getInstance();
	ButtonFactory buttonFactory = new ButtonFactory();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		spieler.setPosition(new Point(0, 0));
		spielfeld.initSpielfeld();
		zeigeGegenstaende();
		starteMenueSetup();
		aufenthaltsraumTf.getStyleClass().add("aufenthaltsraumTf"); // TODO Konstante
		zeigeOptionenAufenthaltsraum();
		starteTastenEventHandler();
		raumButtonEventHandler();
	}

	// Menüsteuerung
	private void starteMenueSetup() {
		spielBeenden.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Spiel wirklich beenden?");
				ButtonType btJa = new ButtonType("Ja");
				ButtonType btAbbruch = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(btJa, btAbbruch);
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == btJa) {
					System.exit(0);
				}
			}
		});
	}

	// TODO in eigene Klasse auslagern
	// Spielersteuerung
	private void starteTastenEventHandler() {
		root.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				for (Button b : listeMitRaumAktionsButtons) {
					switch (event.getCode()) {
						case UP:
							if (b.getId().equals("moveNorth")) {
								textausgabe(spieler.nachNordenBewegen());
								event.consume();
							}
							break;
						case DOWN:
							if (b.getId().equals("moveSouth")) {
								textausgabe(spieler.nachSuedenBewegen());
								event.consume();
							}
							break;
						case LEFT:
							if (b.getId().equals("moveWest")) {
								textausgabe(spieler.nachWestenBewegen());
								event.consume();
							}
							break;
						case RIGHT:
							if (b.getId().equals("moveEast")) {
								textausgabe(spieler.nachOstenBewegen());
								event.consume();
							}
							break;
						default:
							throw new IllegalerRaumException(
									"Spieler kann sich nur in einen bestehenden Raum bewegen.");
					}
				}
				zeigeOptionenAufenthaltsraum();
			}
		});
	}

	public void raumButtonEventHandler() {
		raumButton.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				zeigeOptionenAufenthaltsraum();
			}
		});
	}

	// TODO vor dem ersten Zug wirkt Leerzeile vor dem Aufenthaltsraum merkwürdig.
	// Dort könnte Intro-Text rein?
	private void textausgabe(String ausgabe) {
		zeigeAufenthaltsraum();
		textausgabeTa.appendText( //
				"\n" //
						+ "\n" //
						+ "-- " + currentRoom + " --" + "\n" //
						+ "\n" //
						+ ausgabe);
		bildausgabe(currentRoom);
	}

	// TODO pics ebenfalls in DB auslagern (CDN?)
	private void bildausgabe(String s) {
		// Image image = new
		// Image(getClass().getResourceAsStream("/images/keller.jpg"));
		StringBuilder sb = new StringBuilder();
		sb.append("/dgdlz/images/"); // TODO Konstante für die Pfade
		sb.append(s.toLowerCase());
		sb.append(".png"); // TODO Konstante für die Pfade
		Image image = new Image(getClass().getResourceAsStream(sb.toString()));
		roomIv.setImage(image);
	}

	// TODO muss aktueller Ort noch im Label ausgegeben werden? Siehe laufende
	// Orts-Aktualisierung im textausgabeTa. Label evtl für andere wichtige Werte
	// wie Lebensenergie, Zeit etc. nutzen?
	private void zeigeAufenthaltsraum() {
		currentRoom = spielfeld.ermittleAufenthaltsraumSpieler(spieler).getName();
		aufenthaltsraumTf.setText("Du bist hier: " + currentRoom);
	}

	private void zeigeOptionenAufenthaltsraum() {
		starteZugLogik();
		starteErkundungsLogik(); // TODO
		zeigeAufenthaltsraum();
	}

	private void zeigeGegenstaende() {
		gegenstandButton.setOnAction(e -> {
			starteGegenstandsOptionenLogik();
		});
	}

	private void starteGegenstandsOptionenLogik() {
		gegenstandAktionenVb.getChildren().clear();
		listeMitGegenstandsAktionsButtons.clear();
		gegenstandsAktionsButtonsVorbereiten();
	}

	private void gegenstandsAktionsButtonsVorbereiten() {
		for (Gegenstand g : spielfeld.getAlleGegenstaende()) {
			listeMitGegenstandsAktionsButtons.add(new Button(g.getName()));
		}
		for (Button button : listeMitGegenstandsAktionsButtons) {
			buttonMitStyleVersehenUndEinhaengenGegenstaende(button);
		}
	}

	// TODO Zug soll sofort Optionen in vBoxOben aktualisieren und nicht erst bei
	// Klick auf "Raum"!
	private void starteZugLogik() {
		raumAktionenVb.getChildren().clear();
		listeMitRaumAktionsButtons.clear();
		spielfeld.ermittleDieNachbarraeume(spieler);
		createMoeglicheZugbuttons();
		himmelsrichtungButtonsVorbereiten();
		himmelsrichtungButtonsAktivieren();
	}

	public void createMoeglicheZugbuttons() {
		for (Raum r : spielfeld.getNachbarraeume()) {
			if (r.getPosition().y == spieler.getPosition().y + 1) {
				listeMitRaumAktionsButtons.add(buttonFactory.createButton("nach Norden gehen", "moveNorth"));
			}
			if (r.getPosition().y == spieler.getPosition().y - 1) {
				listeMitRaumAktionsButtons.add(buttonFactory.createButton("nach Süden gehen", "moveSouth"));
			}
			if (r.getPosition().x == spieler.getPosition().x + 1) {
				listeMitRaumAktionsButtons.add(buttonFactory.createButton("nach Osten gehen", "moveEast"));
			}
			if (r.getPosition().x == spieler.getPosition().x - 1) {
				listeMitRaumAktionsButtons.add(buttonFactory.createButton("nach Westen gehen", "moveWest"));
			}
		}
	}

	private void himmelsrichtungButtonsVorbereiten() {
		for (Button button : listeMitRaumAktionsButtons) {
			buttonMitStyleVersehenUndEinhaengen(button);
		}
	}

	private void himmelsrichtungButtonsAktivieren() {
		for (Button b : listeMitRaumAktionsButtons) {
			switch (b.getId()) {
				case "moveNorth": {
					b.setOnAction(e -> {
						textausgabe(spieler.nachNordenBewegen());
					});
					break;
				}
				case "moveSouth": {
					b.setOnAction(e -> {
						textausgabe(spieler.nachSuedenBewegen());
					});
					break;
				}
				case "moveEast": {
					b.setOnAction(e -> {
						textausgabe(spieler.nachOstenBewegen());
					});
					break;
				}
				case "moveWest": {
					b.setOnAction(e -> {
						textausgabe(spieler.nachWestenBewegen());
					});
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: "); // TODO
			}
			b.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					zeigeOptionenAufenthaltsraum();
				}
			});
		}
	}

	private void starteErkundungsLogik() {
		erkundungsButton.setText(spielfeld.ermittleAufenthaltsraumSpieler(spieler).getName() + " erkunden");
		buttonMitStyleVersehenUndEinhaengen(erkundungsButton);
		erkundungsButtonAktivieren();
	}

	private void erkundungsButtonAktivieren() {
		erkundungsButton.setOnAction(e -> {
			textausgabe(spielfeld.ermittleAufenthaltsraumSpieler(spieler).untersuchen());
		});
	}

	// TODO duplicate Code
	private void buttonMitStyleVersehenUndEinhaengen(Button button) {
		button.getStyleClass().add("buttonAuswahlAktion"); // TODO Konstante
		button.setAlignment(Pos.BASELINE_LEFT);
		raumAktionenVb.getChildren().addAll(button);
		button.setMaxWidth(Double.MAX_VALUE);
		VBox.setVgrow(button, Priority.ALWAYS);
	}

	// TODO duplicate Code
	private void buttonMitStyleVersehenUndEinhaengenGegenstaende(Button button) {
		button.getStyleClass().add("buttonAuswahlAktion"); // TODO Konstante
		button.setAlignment(Pos.BASELINE_LEFT);
		gegenstandAktionenVb.getChildren().addAll(button);
		button.setMaxWidth(Double.MAX_VALUE);
		VBox.setVgrow(button, Priority.ALWAYS);
	}

	public Parent getRoot() {
		return root;
	}
}
