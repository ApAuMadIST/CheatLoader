package net.minecraft.src;

public class Timer {
	public float ticksPerSecond;
	private double lastHRTime;
	public int elapsedTicks;
	public float renderPartialTicks;
	public float timerSpeed = 1.0F;
	public float elapsedPartialTicks = 0.0F;
	private long lastSyncSysClock;
	private long lastSyncHRClock;
	private long field_28132_i;
	private double timeSyncAdjustment = 1.0D;

	public Timer(float f1) {
		this.ticksPerSecond = f1;
		this.lastSyncSysClock = System.currentTimeMillis();
		this.lastSyncHRClock = System.nanoTime() / 1000000L;
	}

	public void updateTimer() {
		long j1 = System.currentTimeMillis();
		long j3 = j1 - this.lastSyncSysClock;
		long j5 = System.nanoTime() / 1000000L;
		double d7 = (double)j5 / 1000.0D;
		if(j3 > 1000L) {
			this.lastHRTime = d7;
		} else if(j3 < 0L) {
			this.lastHRTime = d7;
		} else {
			this.field_28132_i += j3;
			if(this.field_28132_i > 1000L) {
				long j9 = j5 - this.lastSyncHRClock;
				double d11 = (double)this.field_28132_i / (double)j9;
				this.timeSyncAdjustment += (d11 - this.timeSyncAdjustment) * (double)0.2F;
				this.lastSyncHRClock = j5;
				this.field_28132_i = 0L;
			}

			if(this.field_28132_i < 0L) {
				this.lastSyncHRClock = j5;
			}
		}

		this.lastSyncSysClock = j1;
		double d13 = (d7 - this.lastHRTime) * this.timeSyncAdjustment;
		this.lastHRTime = d7;
		if(d13 < 0.0D) {
			d13 = 0.0D;
		}

		if(d13 > 1.0D) {
			d13 = 1.0D;
		}

		this.elapsedPartialTicks = (float)((double)this.elapsedPartialTicks + d13 * (double)this.timerSpeed * (double)this.ticksPerSecond);
		this.elapsedTicks = (int)this.elapsedPartialTicks;
		this.elapsedPartialTicks -= (float)this.elapsedTicks;
		if(this.elapsedTicks > 10) {
			this.elapsedTicks = 10;
		}

		this.renderPartialTicks = this.elapsedPartialTicks;
	}
}
