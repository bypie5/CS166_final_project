import java.awt.*;
import java.awt.event.*;

public class AddTechUI extends QueryUI {
	Label title;
	Label l1;
	TextField tf1;
	Panel title_panel;
	Panel name_panel;
	Panel submit_panel;
	Dialog update_message;

	public AddTechUI (InputVessel[] vessels) {
		super(vessels);
	}

	@Override
	public void runUI() {
		Frame frame = new Frame("Add Technician");
		update_message = new Dialog(frame);
		update_message.setLayout(new FlowLayout());

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				setReady();
				frame.dispose();
			}		
		});

		frame.setLayout(new GridLayout(vessels.length + 2, 1));
		frame.setSize(400, 75 *3 * vessels.length);
		
		// Components
		
		// Title of the query
		title_panel = new Panel(new FlowLayout());
		title = new Label("Add Technician", Label.CENTER);
		title_panel.add(title);

		name_panel = new Panel(new FlowLayout());
		l1 = new Label("full name: ", Label.RIGHT);
		tf1 = new TextField(15);
		name_panel.add(l1);
		name_panel.add(tf1);

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
			
				update_message.add(new Label("Successful Update to Technician"));
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
			}
		});

		// Add individual panels to main frame
		frame.add(title_panel);
		frame.add(name_panel);
		frame.add(submit_panel);
		frame.setVisible(true);

		while (!getReady()) {
			// Busy wait
		}
	}
}
