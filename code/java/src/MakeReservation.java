import java.awt.*;
import java.awt.event.*;

public class MakeReservation extends QueryUI {
	// Given cid and fid, check if you can actually make a reservation
	Label title;
	Label l1;
	Label l2;
	TextField tf1;
	TextField tf2;
	Panel title_panel;
	Panel cid;
	Panel fid;
	Panel submit_panel;

	public MakeReservation(InputVessel[] vessels) {
		super(vessels);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame("Make Reservation");
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				setReady();
				frame.dispose();
			}		
		});

		frame.setLayout(new GridLayout(vessels.length + 2, 1));
		frame.setSize(400, 75 * vessels.length);
		
		// Components
		
		// Title of the query
		title_panel = new Panel(new FlowLayout());
		title = new Label("Make Reservation", Label.CENTER);
		title_panel.add(title);

		cid = new Panel(new FlowLayout());
		l1 = new Label("Customer ID: ", Label.RIGHT);
		tf1 = new TextField(6);
		cid.add(l1);
		cid.add(tf1);

		fid = new Panel(new FlowLayout());
		l2 =  new Label("Flight Number: ", Label.RIGHT);
		tf2 = new TextField(6);
		fid.add(l2);
		fid.add(tf2);
		
		// Button used to submit
		submit_panel = new Panel(new FlowLayout());
		Button submit = new Button("Insert Entry");
		submit_panel.add(submit);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setReady();
				setDoQuery();

				// Send inputs to vessels
				vessels[0].assignValue(tf1.getText());
				vessels[1].assignValue(tf2.getText());

				// Clear UI text 
				tf1.setText("");
				tf2.setText("");

				frame.dispose();
			}
		});

		// Add individual panels to main frame
		frame.add(title_panel);
		frame.add(cid);
		frame.add(fid);
		frame.add(submit_panel);
		frame.setVisible(true);

		while (!getReady()) {
			// Busy wait
		}
	}
}
