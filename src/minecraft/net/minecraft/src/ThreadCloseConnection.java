package net.minecraft.src;

class ThreadCloseConnection extends Thread {
	final NetworkManager field_28109_a;

	ThreadCloseConnection(NetworkManager networkManager1) {
		this.field_28109_a = networkManager1;
	}

	public void run() {
		try {
			Thread.sleep(2000L);
			if(NetworkManager.isRunning(this.field_28109_a)) {
				NetworkManager.getWriteThread(this.field_28109_a).interrupt();
				this.field_28109_a.networkShutdown("disconnect.closed", new Object[0]);
			}
		} catch (Exception exception2) {
			exception2.printStackTrace();
		}

	}
}
