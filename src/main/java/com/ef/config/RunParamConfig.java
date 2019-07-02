package com.ef.config;

import com.ef.model.Duration;
import com.ef.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * @author bashir
 * @since 30/6/19.
 */
@Configuration
public class RunParamConfig {

    @Value("${startDate}")
    private String startDateStr;

    private LocalDateTime startDate;

    @Value("${duration}")
    private String durationStr;

    private Duration duration;

    @Value("${threshold}")
    private String thresholdStr;

    private Integer threshold;

    @Value("${accesslog}")
    private String accessLogPath;

    private boolean valid;

    @PostConstruct
    public void init() {
        this.valid = Utils.RUN_PARAM_VALIDATOR.test(this);
        if (valid) {
            startDate = Utils.parseDate(startDateStr);
            duration = Utils.parseDuration(durationStr);
            threshold = Utils.parseThreshold(thresholdStr);
        }
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getDurationStr() {
        return durationStr;
    }

    public void setDurationStr(String durationStr) {
        this.durationStr = durationStr;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getThresholdStr() {
        return thresholdStr;
    }

    public void setThresholdStr(String thresholdStr) {
        this.thresholdStr = thresholdStr;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public String getAccessLogPath() {
        return accessLogPath;
    }

    public void setAccessLogPath(String accessLogPath) {
        this.accessLogPath = accessLogPath;
    }
}