import java.awt.*;
import java.awt.event.*;

public class AddPlaneUI extends QueryUI {
	Label title;
	Label l1;
	Label l2;
	Label l3;
	Label l4;
	TextField tf1;
	TextField tf2;
	TextField tf3;
	TextField tf4;
	Panel title_panel;
	Panel make_panel;
	Panel age_panel;
	Panel seats_panel;
	Panel model_panel;
	Panel submit_panel;
	Dialog update_message;

	public AddPlaneUI (InputVessel[] vessels) {
		super(vessels);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame("Add Plane");
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
		
		// Title of the query
		title_panel = new Panel(new FlowLayout());
		title = new Label("Add Plane", Label.CENTER);
		title_panel.add(title);

		make_panel = new Panel(new FlowLayout());
		l1 = new Label("make: ", Label.RIGHT);
		tf1 = new TextField(6);
		make_panel.add(l1);
		make_panel.add(tf1);

		age_panel = new Panel(new FlowLayout());
		l2 = new Label("age: ", Label.RIGHT);
		tf2 = new TextField(6);
		age_panel.add(l2);
		age_panel.add(tf2);

		seats_panel = new Panel(new FlowLayout());
		l3 = new Label("seats: ", Label.RIGHT);
		tf3 = new TextField(6);
		seats_panel.add(l3);
		seats_panel.add(tf3);

		model_panel = new Panel(new FlowLayout());
		l4 = new Label("model: ", Label.RIGHT);
		tf4 = new TextField(6);
		model_panel.add(l4);
		model_panel.add(tf4);

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
				vessels[1].assignValue(tf4.getText());	
				vessels[2].assignValue(tf2.getText());
				vessels[3].assignValue(tf3.getText());
			
				update_message.add(new Label("Successful Update to Plane"));
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
			}
		});

		// Add individual panels to main frame
		frame.add(title_panel);
		frame.add(make_panel);
		frame.add(age_panel);
		frame.add(seats_panel);
		frame.add(model_panel);
		frame.add(submit_panel);
		frame.setVisible(true);

		while (!getReady()) {
			// Busy wait
		}
	}
}
