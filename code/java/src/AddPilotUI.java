import java.awt.*;
import java.awt.event.*;

public class AddPilotUI extends QueryUI {
	
	// cost, num_sold, num_stops, actual_departure_date,
	// actual_arrival_date, arrival_airport, departure_airport
	Label title;
	//Label l1;
	Label l2;
	Label l3;
	//TextField tf1;
	TextField tf2;
	TextField tf3;
	
	Panel title_panel;

	//Panel id;
	Panel fullname;
	Panel nationality;

	Panel submit_panel;
	Dialog update_message;

	public AddPilotUI (InputVessel[] vessels) {
		super(vessels);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame("Add Pilot");
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
		title = new Label("Add Pilot", Label.CENTER);
		title_panel.add(title);

		/*id = new Panel(new FlowLayout());
		l1 = new Label("id: ", Label.RIGHT);
		tf1 = new TextField(6);
		id.add(l1);
		id.add(tf1);*/

		fullname = new Panel(new FlowLayout());
		l2 = new Label("fullname: ", Label.RIGHT);
		tf2 = new TextField(15);
		fullname.add(l2);
		fullname.add(tf2);

		nationality = new Panel(new FlowLayout());
		l3 = new Label("nationality: ", Label.RIGHT);
		tf3 = new TextField(15);
		nationality.add(l3);
		nationality.add(tf3);
	
		submit_panel = new Panel(new FlowLayout());
		Button submit = new Button("Insert Entry");
		submit_panel.add(submit);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setReady();
				setDoQuery();

				// Pass inputs to vessels
				vessels[0].assignValue(tf2.getText());
				vessels[1].assignValue(tf3.getText());
				//vessels[2].assignValue(tf3.getText());
			
				update_message.add(new Label("Successful Update to Pilot"));
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
				//tf1.setText("");
				tf2.setText("");
				tf3.setText("");
			}	
		});

		frame.add(title_panel);
		//frame.add(id);
		frame.add(fullname);
		frame.add(nationality);
		frame.add(submit_panel);
		frame.setVisible(true);

		while(!getReady()) {}

	}
}


