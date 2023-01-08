package net.minecraft.src;

class NetworkReaderThread extends Thread {
	final NetworkManager netManager;

	NetworkReaderThread(NetworkManager networkManager1, String string2) {
		super(string2);
		this.netManager = networkManager1;
	}

	public void run() {
		Object object1 = NetworkManager.threadSyncObject;
		synchronized(NetworkManager.threadSyncObject) {
			++NetworkManager.numReadThreads;
		}

		while(true) {
			boolean z12 = false;

			try {
				z12 = true;
				if(!NetworkManager.isRunning(this.netManager)) {
					z12 = false;
					break;
				}

				if(NetworkManager.isServerTerminating(this.netManager)) {
					z12 = false;
					break;
				}

				while(NetworkManager.readNetworkPacket(this.netManager)) {
				}

				try {
					sleep(100L);
				} catch (InterruptedException interruptedException15) {
				}
			} finally {
				if(z12) {
					Object object5 = NetworkManager.threadSyncObject;
					synchronized(NetworkManager.threadSyncObject) {
						--NetworkManager.numReadThreads;
					}
				}
			}
		}

		object1 = NetworkManager.threadSyncObject;
		synchronized(NetworkManager.threadSyncObject) {
			--NetworkManager.numReadThreads;
		}
	}
}
