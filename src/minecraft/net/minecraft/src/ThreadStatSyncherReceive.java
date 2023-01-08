package net.minecraft.src;

class ThreadStatSyncherReceive extends Thread {
	final StatsSyncher field_27231_a;

	ThreadStatSyncherReceive(StatsSyncher statsSyncher1) {
		this.field_27231_a = statsSyncher1;
	}

	public void run() {
		try {
			if(StatsSyncher.func_27422_a(this.field_27231_a) != null) {
				StatsSyncher.func_27412_a(this.field_27231_a, StatsSyncher.func_27422_a(this.field_27231_a), StatsSyncher.func_27423_b(this.field_27231_a), StatsSyncher.func_27411_c(this.field_27231_a), StatsSyncher.func_27413_d(this.field_27231_a));
			} else if(StatsSyncher.func_27423_b(this.field_27231_a).exists()) {
				StatsSyncher.func_27421_a(this.field_27231_a, StatsSyncher.func_27409_a(this.field_27231_a, StatsSyncher.func_27423_b(this.field_27231_a), StatsSyncher.func_27411_c(this.field_27231_a), StatsSyncher.func_27413_d(this.field_27231_a)));
			}
		} catch (Exception exception5) {
			exception5.printStackTrace();
		} finally {
			StatsSyncher.func_27416_a(this.field_27231_a, false);
		}

	}
}
