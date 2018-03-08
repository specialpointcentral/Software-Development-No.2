package action;

import java.util.concurrent.ExecutorService;

public class LastWorks extends Thread {
	private ExecutorService exec;

	public LastWorks(ExecutorService exec) {
		this.exec = exec;
	}

	public void run() {
		while (true) {
			if (exec.isTerminated()) {
				System.out.println("≥Ã–Ú“—Ω· ¯");
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
