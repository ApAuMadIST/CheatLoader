package net.minecraft.src;

class ThreadRunIsoClient extends Thread {
	final CanvasIsomPreview isoCanvas;

	ThreadRunIsoClient(CanvasIsomPreview canvasIsomPreview1) {
		this.isoCanvas = canvasIsomPreview1;
	}

	public void run() {
		while(CanvasIsomPreview.isRunning(this.isoCanvas)) {
			this.isoCanvas.showNextBuffer();

			try {
				Thread.sleep(1L);
			} catch (Exception exception2) {
			}
		}

	}
}
