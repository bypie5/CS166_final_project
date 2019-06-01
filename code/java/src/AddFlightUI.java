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
	Panel actual_departure_date;
	Panel actual_arrival_date;
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

		sold = new Panel(new FlowLayout());
		l2 = new Label("sold: ", Label.RIGHT);
		tf2 = new TextField(6);
		sold.add(l2);
		sold.add(tf2);

		stops = new Panel(new FlowLayout());
		l3 = new Label("stops: ", Label.RIGHT);
		tf3 = new TextField(6);
		stops.add(l3);
		stops.add(tf3);

		actual_departure_date = new Panel(new FlowLayout());
		l4 = new Label("depature date: ", Label.RIGHT);
		tf4 = new TextField(6);
		actual_departure_date.add(l4);
		actual_departure_date.add(tf4);

		actual_arrival_date = new Panel(new FlowLayout());
		l5 = new Label("drrival date: ", Label.RIGHT);
		tf5 = new TextField(6);
		actual_arrival_date.add(l5);
		actual_arrival_date.add(tf5);

		departure = new Panel(new FlowLayout());
		l6 = new Label("departure airport: ", Label.RIGHT);
		tf6 = new TextField(6);
		departure.add(l6);
		departure.add(tf6);

		arrival = new Panel(new FlowLayout());
		l7 = new Label("arrival airport: ", Label.RIGHT);
		tf7 = new TextField(6);
		arrival.add(l7);
		arrival.add(tf7);


		submit_panel = new Panel(new FlowLayout());
		Button submit = new Button("Insert Entry");
		submit_panel.add(submit);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setReady();
				setDoQuery();

				// Pass inputs to vessels
				vessels[0].assignValue(tf1.getText());
				vessels[1].assignValue(tf2.getText());
				vessels[2].assignValue(tf3.getText());
				vessels[3].assignValue(tf4.getText());
				vessels[4].assignValue(tf5.getText());
				vessels[5].assignValue(tf7.getText());
				vessels[6].assignValue(tf6.getText());

				update_message.add(new Label("Successful Update to Flight"));
				Button b = new Button("OK");
				update_message.add(b);
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						update_message.setVisible(false);
						frame.dispose();
					}
				});
				
				update_message.setSize(250, 250);
				update_message.setVisible(true);

				// Clear UI text
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				tf4.setText("");
				tf5.setText("");
				tf6.setText("");
				tf7.setText("");
			}	
		});

		frame.add(title_panel);
		frame.add(cost);
		frame.add(sold);
		frame.add(stops);
		frame.add(actual_departure_date);
		frame.add(actual_arrival_date);
		frame.add(arrival);
		frame.add(departure);
		frame.add(submit_panel);
		frame.setVisible(true);

		while(!getReady()) {}

	}
}

