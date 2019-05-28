import java.awt.*;
import java.awt.event.*;

public class AddPlaneUI extends QueryUI {
	Label l1;
	Label l2;
	TextField tf1;
	TextField tf2;
	Panel id_panel;
	Panel make_panel;

	public AddPlaneUI (InputVessel[] vessels) {
		super(vessels);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame("Add Plane");

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				setReady();
				
				// Send inputs to vessels
				vessels[0].assignValue(tf1.getText());
				vessels[1].assignValue(tf2.getText());	
				
				frame.dispose();
			}		
		});

		frame.setLayout(new GridLayout(vessels.length, 1));
		frame.setSize(400, 100 * vessels.length);
		
		// Components
		id_panel = new Panel(new FlowLayout());
		l1 = new Label("id: ", Label.RIGHT);
		tf1 = new TextField(6);
		id_panel.add(l1);
		id_panel.add(tf1);

		make_panel = new Panel(new FlowLayout());
		l2 = new Label("make: ", Label.RIGHT);
		tf2 = new TextField(6);
		make_panel.add(l2);
		make_panel.add(tf2);

		frame.add(id_panel);
		frame.add(make_panel);
		frame.setVisible(true);

		while (!getReady()) {
			// Busy wait
		}

		System.out.println("Returned");
	}
}
