package com.ef.model;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * @author bashir
 * @since 2/7/19.
 */
public enum Duration {
    HOURLY(start -> start.plusHours(1)),
    DAILY(start -> start.plusDays(1));

    private final Function<LocalDateTime, LocalDateTime> endTimeSupplier;

    Duration(Function<LocalDateTime, LocalDateTime> endTimeSupplier) {
        this.endTimeSupplier = endTimeSupplier;
    }

    public LocalDateTime getEndTimeFor(LocalDateTime start) {
        return this.endTimeSupplier.apply(start);
    }
}