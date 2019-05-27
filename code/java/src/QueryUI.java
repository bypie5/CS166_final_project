class UIPoller extends Thread {
	QueryUI qui;

	UIPoller (QueryUI qui) {
		this.qui = qui;	
	}

	public void run() {
		try {
			qui.runUI();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}

public abstract class QueryUI {
	private InputVessel[] vessels;

	volatile private boolean ready;

	public QueryUI(InputVessel[] vessels) {
		this.vessels = vessels;
		this.ready = false;
	}

	public void pollInput() {
		UIPoller poller = new UIPoller(this);
		poller.start();

		try {
			poller.join();
		} catch (Exception e) {
			System.out.println("Thread error: " + e.getMessage());
		}
	}	

	public boolean getReady() {
		return this.ready;
	}

	public void setReady() {
		this.ready = true;
	}

	// This function gets run on a seperate thread
	public abstract void runUI();
};

