package util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeHandler {
    public static final int FAST_TIME = 300;
    public static final int NORMAL_TIME = 500;
    public static final int SLOW_TIME = 1000;

    private TimeHandler() {
        // Private constructor
    }

    public static ScheduledExecutorService delay(Runnable task, int milliseconds, boolean repeats) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        if (repeats) {
            executor.scheduleAtFixedRate(task, 0, milliseconds, TimeUnit.MILLISECONDS);
            return executor;
        }

        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);

        return executor;
    }

}
