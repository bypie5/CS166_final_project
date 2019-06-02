import java.awt.*;
import java.awt.event.*;

public class GetSeatsUI extends QueryUI {
	Label title;
	Label l1;
	Label l2;
	TextField tf1;
	//TextField tf2;
	Panel title_panel;
	Panel fnum_panel;
	//Panel date_panel;
	Panel submit_panel;

	public GetSeatsUI (InputVessel[] vessels) {
		super(vessels);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame("Get Seats");
		
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
		title = new Label("Get Seats", Label.CENTER);
		title_panel.add(title);

		fnum_panel = new Panel(new FlowLayout());
		l1 = new Label("flight number: ", Label.RIGHT);
		tf1 = new TextField(15);
		fnum_panel.add(l1);
		fnum_panel.add(tf1);

		/*date_panel = new Panel(new FlowLayout());
		l2 = new Label("departure date: ", Label.RIGHT);
		tf2 = new TextField(15);
		date_panel.add(l2);
		date_panel.add(tf2);*/

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
				//vessels[1].assignValue(tf2.getText());

				// Clear UI text 
				tf1.setText("");
				//tf2.setText("");
				
				frame.dispose();
			}
		});

		// Add individual panels to main frame
		frame.add(title_panel);
		frame.add(fnum_panel);
		//frame.add(date_panel);
		frame.add(submit_panel);
		frame.setVisible(true);

		while (!getReady()) {
			// Busy wait
		}
	}
}

