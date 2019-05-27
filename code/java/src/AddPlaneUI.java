import java.awt.*;
import java.awt.event.*;

public class AddPlaneUI extends QueryUI {
	public AddPlaneUI (InputVessel[] v) {
		super(v);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				setReady();
				frame.dispose();
			}		
		});

		Label title = new Label("Add Plane", Label.CENTER);

		frame.add(title);
		frame.setSize(300, 300);
		frame.setVisible(true);
		
		while (!getReady()) {
			// Busy wait
		}

		System.out.println("Returned");
	}
}
