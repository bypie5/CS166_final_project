import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;


public class ResultsUI {
	List<List<String>> rows;
	String title;

	// UI Elements
	Frame frame;
	TextArea mainDisplay;
	Panel title_panel;
	Panel display_panel;

	public ResultsUI(String title, List<List<String>> rows) {
		this.rows = rows;
		this.title = title;
	}

	public void createUI() {
		frame = new Frame(title);
		frame.setLayout(new GridLayout(1,1));
		frame.setSize(600, 400);
		
		frame.addWindowListener(new WindowAdapter() {
			// Window closes when x button is pressed
			public void windowClosing(WindowEvent windowEvent) {
				frame.dispose();
			}		
		});

		title_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
		Label title = new Label(this.title, Label.CENTER);
		title_panel.add(title);

		display_panel = new Panel(new FlowLayout());
		mainDisplay = new TextArea(20, 60);
		mainDisplay.setEditable(false);

		// Append text to the mainDisplay
		ListIterator<List<String>> rows_it = rows.listIterator();
		
		while (rows_it.hasNext()) {
			List<String> currCol = rows_it.next();
			ListIterator<String> col_it = currCol.listIterator();
			while(col_it.hasNext()) {
				mainDisplay.append(col_it.next() + " ");	
			}

			// Get ready for the next row
			mainDisplay.append("\n");
		}

		title_panel.add(mainDisplay);

		frame.add(title_panel);
		frame.setVisible(true);		
	}
}

