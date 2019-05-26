class UIPoller extends Thread {
	QueryUI qui;

	UIPoller (QueryUI qui) {
		this.qui = qui;	
	}

	public void run() {
		try {
			// Wait until this function returns
			qui.runUI();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}

public abstract class QueryUI {
	private InputVessel[] vessels;

	public QueryUI(InputVessel[] vessels) {
		this.vessels = vessels;
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

	public abstract void runUI();
};

