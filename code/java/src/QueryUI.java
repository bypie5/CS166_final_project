import java.awt.*;
import java.awt.event.*;

class UIPoller extends Thread {
	QueryUI qui;

	UIPoller (QueryUI qui) {
		this.qui = qui;	
	}

	public void run() {
		try {
			qui.runUI();
		} catch (Exception e) {
			System.out.println("UIPoller Error!");
			System.out.println("Exception: " + e);
		}
	}
}

public abstract class QueryUI {
	public InputVessel[] vessels;

	volatile private boolean ready;
	volatile private boolean doQuery;

	public QueryUI(InputVessel[] vessels) {
		this.vessels = vessels;
		this.ready = false;
		this.doQuery = false;
	}

	public void pollInput() {
		UIPoller poller = new UIPoller(this);
		poller.start();

		try {
			poller.join();
			this.ready = false;
		} catch (Exception e) {
			System.out.println("Thread error: " + e.getMessage());
		}
	}	

	public boolean getReady() {
		return this.ready;
	}

	public boolean getDoQuery() {
		return this.doQuery;
	}

	public void setReady() {
		this.ready = true;
	}

	public void setDoQuery() {
		this.doQuery = true;
	}

	public void displayMessage(String msg) {
		// Create a dialog to show the menu
		Frame alertWindow = new Frame("Alert");
		alertWindow.setSize(250, 75);

		alertWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				alertWindow.dispose();
			}
		});

		Label label = new Label(msg, Label.CENTER);
		alertWindow.add(label);
		alertWindow.setVisible(true);
	}

	// This function gets run on a seperate thread
	public abstract void runUI();
};

