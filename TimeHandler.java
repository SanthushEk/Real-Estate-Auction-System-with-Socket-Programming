import java.util.concurrent.atomic.AtomicLong;

public class TimeHandler {

	private final AtomicLong endTime;

	public TimeHandler(long time) {
		this.endTime = new AtomicLong(System.currentTimeMillis() + time);
	}

	public boolean isTimeout() {
		return System.currentTimeMillis() > endTime.get();
	}

	public long getRemainingTime() {
		return Math.max(0, endTime.get() - System.currentTimeMillis());
	}

	public void incrementTime(long time) {
		endTime.addAndGet(time);
	}

}
