import java.awt.*;
import java.awt.event.*;

public class AddPlaneUI extends QueryUI {
	Label l1;
	TextField tf1;
		
	public AddPlaneUI (InputVessel[] v) {
		super(v);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame("Add Plane");

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				setReady();
				vessels[0].assignValue(tf1.getText());
				frame.dispose();
			}		
		});

		frame.setLayout(new FlowLayout());
		frame.setSize(400, 400);
		frame.setVisible(true);
		
		// Components
		l1 = new Label("id: ", Label.RIGHT);
		tf1 = new TextField(20);
		frame.add(l1);
		frame.add(tf1);

		while (!getReady()) {
			// Busy wait
		}

		System.out.println("Returned");
	}
}
