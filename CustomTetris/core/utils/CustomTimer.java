package core.utils;

public class CustomTimer {
    private long startTime;
    private long stopTime;
    private boolean stopped;
    public CustomTimer() {
    }

    public void start() {
        startTime = System.currentTimeMillis();
        stopped = false;
    }

    public long timeElapsed() {
        if (stopped) {
            return stopTime;
        } else {
            return System.currentTimeMillis() - startTime;
        }
    }
    public void stop() {
        stopTime = System.currentTimeMillis() - startTime;
        stopped = true;
    }
    public void resume() {
        startTime = System.currentTimeMillis() - stopTime;
        stopped = false;
    }

    public long[] countTime() {
        long[] times = new long[3];
        long mins = (timeElapsed() / 1000) / 60;
        long secs = (timeElapsed() / 1000) % 60;
        long mills = (timeElapsed() % 1000);
        times[0] = mins;
        times[1] = secs;
        times[2] = mills;
        return times;
    }
}
