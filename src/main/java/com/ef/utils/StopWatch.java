package com.ef.utils;

import static java.util.Objects.requireNonNull;

/**
 * @author bashir
 * @since 2/7/19.
 */
public class StopWatch {

    private Long start;

    private StopWatch(long start) {
        this.start = start;
    }

    public static StopWatch start() {
        return new StopWatch(System.currentTimeMillis());
    }

    public long elapsedTime() {
        requireNonNull(start);
        return System.currentTimeMillis() - start;
    }
}
