package dgdlz;

import static com.google.common.base.Preconditions.checkNotNull;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Bewegungstracker {

	Button btn;

	Bewegungstracker(Button btn) {
		this.btn = checkNotNull(btn, "btn == null!");
	}

	public void trackeButtonklick() {
		this.btn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("hallo"); // TODO
			}
		});
	}

}
