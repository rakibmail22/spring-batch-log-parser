package com.ef.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.ef.utils.Utils.DATE_TIME_FORMATTER_24H;

/**
 * @author bashir
 * @since 2/7/19.
 */
public class BlockedEntry implements Serializable {

    private static final long serialVersionUID = 3L;

    private String ip;

    private LocalDateTime startDate;

    private Duration duration;

    private int threshold;

    private int requestCount;

    public BlockedEntry(String ip, LocalDateTime startDate,
                        Duration duration, int threshold, int requestCount) {
        this.ip = ip;
        this.startDate = startDate;
        this.duration = duration;
        this.threshold = threshold;
        this.requestCount = requestCount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public String getComments() {
        return "IP: " + getIp() + " blocked for request " + getRequestCount() + " times from "
                + getStartDate().format(DATE_TIME_FORMATTER_24H)
                + " to " + duration.getEndTimeFor(startDate).format(DATE_TIME_FORMATTER_24H);
    }

    @Override
    public String toString() {
        return "BlockedEntry{" +
                "ip='" + ip + '\'' +
                ", startDate=" + startDate +
                ", duration=" + duration +
                ", threshold=" + threshold +
                ", requestCount=" + requestCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockedEntry)) return false;

        BlockedEntry that = (BlockedEntry) o;

        if (requestCount != that.requestCount) return false;
        if (threshold != that.threshold) return false;
        if (duration != that.duration) return false;
        if (!ip.equals(that.ip)) return false;
        if (!startDate.equals(that.startDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ip.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + threshold;
        result = 31 * result + requestCount;
        return result;
    }
}
