package dgdlz;

import javafx.scene.control.Button;

public class ButtonFactory {

	public Button createButton(String beschriftung, String id) {
		Button b = new Button();
		b.setText(beschriftung);
		b.setId(id);
		return b;
	}

}
