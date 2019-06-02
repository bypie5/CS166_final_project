import java.awt.*;
import java.awt.event.*;

public class PassengerUI extends QueryUI {
	Label title;
	Label l1;
	Label l2;
	TextField tf1;
	Panel title_panel;
	Panel status_panel;
	Panel submit_panel;

	public PassengerUI (InputVessel[] vessels) {
		super(vessels);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame("Number of Passengers by Status");
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				setReady();
				frame.dispose();
			}		
		});

		frame.setLayout(new GridLayout(vessels.length + 2, 1));
		frame.setSize(400, 75 * 3 * vessels.length);
		
		// Components
		
		// Title of the query
		title_panel = new Panel(new FlowLayout());
		title = new Label("Number of Passenger by Status", Label.CENTER);
		title_panel.add(title);

		status_panel = new Panel(new FlowLayout());
		l1 = new Label("Status (C, W, R): ", Label.RIGHT);
		tf1 = new TextField(3);
		status_panel.add(l1);
		status_panel.add(tf1);

		// Button used to submit
		submit_panel = new Panel(new FlowLayout());
		Button submit = new Button("Start Query");
		submit_panel.add(submit);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setReady();
				setDoQuery();

				// Send inputs to vessels
				vessels[0].assignValue(tf1.getText());

				// Clear UI text 
				tf1.setText("");
				
				frame.dispose();
			}
		});

		// Add individual panels to main frame
		frame.add(title_panel);
		frame.add(status_panel);
		frame.add(submit_panel);
		frame.setVisible(true);

		while (!getReady()) {
			// Busy wait
		}
	}
}

