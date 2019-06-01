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

	// This function gets run on a seperate thread
	public abstract void runUI();
};

