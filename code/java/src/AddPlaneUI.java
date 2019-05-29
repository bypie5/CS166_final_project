import java.awt.*;
import java.awt.event.*;

public class AddPlaneUI extends QueryUI {
	Label title;
	Label l1;
	Label l2;
	Label l3;
	Label l4;
	Label l5;
	TextField tf1;
	TextField tf2;
	TextField tf3;
	TextField tf4;
	TextField tf5;
	Panel title_panel;
	Panel id_panel;
	Panel make_panel;
	Panel age_panel;
	Panel seats_panel;
	Panel model_panel;
	Panel submit_panel;

	public AddPlaneUI (InputVessel[] vessels) {
		super(vessels);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame("Add Plane");

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
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

		age_panel = new Panel(new FlowLayout());
		l3 = new Label("age: ", Label.RIGHT);
		tf3 = new TextField(6);
		age_panel.add(l3);
		age_panel.add(tf3);

		seats_panel = new Panel(new FlowLayout());
		l4 = new Label("seats: ", Label.RIGHT);
		tf4 = new TextField(6);
		seats_panel.add(l4);
		seats_panel.add(tf4);

		model_panel = new Panel(new FlowLayout());
		l5 = new Label("model: ", Label.RIGHT);
		tf5 = new TextField(6);
		model_panel.add(l5);
		model_panel.add(tf5);

		// Button used to submit
		submit_panel = new Panel(new FlowLayout());
		Button submit = new Button("Insert Entry");
		submit_panel.add(submit);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setReady();
				
				// Send inputs to vessels
				vessels[0].assignValue(tf1.getText());
				vessels[1].assignValue(tf2.getText());	
				vessels[2].assignValue(tf3.getText());
				vessels[3].assignValue(tf4.getText());
				vessels[4].assignValue(tf5.getText());
			}
		});

		// Add individual panels to main frame
		frame.add(title_panel);
		frame.add(id_panel);
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
