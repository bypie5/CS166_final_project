import java.awt.*;
import java.awt.event.*;

public class AddFlightUI extends QueryUI {
	
	// cost, num_sold, num_stops, actual_departure_date,
	// actual_arrival_date, arrival_airport, departure_airport
	Label title;
	Label l1;
	Label l2;
	Label l3;
	Label l4;
	Label l5;
	Label l6;
	Label l7;
	TextField tf1;
	TextField tf2;
	TextField tf3;
	TextField tf4;
	TextField tf5;
	TextField tf6;
	TextField tf7;
	Panel title_panel;
	Panel cost;
	Panel sold;
	Panel stops;
	Panel departure_date;
	Panel arrival_date;
	Panel arrival;
	Panel departure;
	Panel submit_panel;
	Dialog update_message;

	public AddFlightUI (InputVessel[] vessels) {
		super(vessels);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame("Add Flight");
		update_message = new Dialog(frame);
		update_message.setLayout(new FlowLayout());

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				setReady();
				frame.dispose();
			}		
		});

		frame.setLayout(new GridLayout(vessels.length + 2, 1));
		frame.setSize(400, 75 * vessels.length);

		// Components
		title_panel = new Panel(new FlowLayout());
		title = new Label("Add Plane", Label.CENTER);
		title_panel.add(title);

		cost = new Panel(new FlowLayout());
		l1 = new Label("cost: ", Label.RIGHT);
		tf1 = new TextField(6);
		cost.add(l1);
		cost.add(tf1);


		submit_panel = new Panel(new FlowLayout());
		Button submit = new Button("Insert Entry");
		submit_panel.add(submit);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setReady();
				setDoQuery();

				// Pass inputs to vessels

				update_message.add(new Label("Successful Update to Flight"));
				Button b = new Button("OK");
				update_message.add(b);
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						update_message.setVisible(false);
					}
				});
				
				update_message.setSize(250, 250);
				update_message.setVisible(true);

				// Clear UI text
			}
		});

		frame.add(title_panel);
		frame.add(cost);
		//frame.add(sold);
		//frame.add(stops);
		//frame.add(departure_date);
		//frame.add(arrival_date);
		//frame.add(arrival);
		//frame.add(departure);
		frame.add(submit_panel);
		frame.setVisible(true);

		while(!getReady()) {}

	}
}

