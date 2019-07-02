package com.ef.utils;

import com.ef.config.RunParamConfig;
import com.ef.model.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.nonNull;

/**
 * @author bashir
 * @since 2/7/19.
 */
public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static final DateTimeFormatter DATE_TIME_FORMATTER_24H = ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static final DateTimeFormatter INPUT_DATE_TIME_FORMATTER_24H = ofPattern("yyyy-MM-dd.HH:mm:ss");

    public static final Predicate<RunParamConfig> RUN_PARAM_VALIDATOR = runParamConfig ->
            nonNull(parseDate(runParamConfig.getStartDateStr()))
                    && nonNull(parseDuration(runParamConfig.getDurationStr()))
                    && nonNull(parseThreshold(runParamConfig.getThresholdStr()))
                    && isValidPath(runParamConfig.getAccessLogPath());

    public static final String INVALID_ARG_STR = "Invalid Argument." +
            " Please provide accesslog=/path/to/log," +
            " startDate=yyyy-MM-dd.HH:mm:ss," +
            " duration=hourly, threshold=integer as argument.";

    public static LocalDateTime parseDate(String dateStr) {
        log.debug("[Util::parseDate] date {}", dateStr);
        try {
            return LocalDateTime.parse(dateStr, INPUT_DATE_TIME_FORMATTER_24H);
        } catch (DateTimeParseException | NullPointerException ex) {
            log.debug(ex.getMessage());
            return null;
        }
    }

    public static Duration parseDuration(String durationStr) {
        log.debug("[Util::parseDuration] duration {}", durationStr);
        try {
            return Duration.valueOf(durationStr.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException ex) {
            log.debug(ex.getMessage());
            return null;
        }
    }

    public static Integer parseThreshold(String threshold) {
        log.debug("[Util::parseThreshold] threshold {}", threshold);
        try {
            return Integer.valueOf(threshold);
        } catch (NumberFormatException | NullPointerException ex) {
            log.debug(ex.getMessage());
            return null;
        }
    }

    public static boolean isValidPath(String path) {
        log.debug("[Util::isValidPath] path {}", path);

        return !StringUtils.isEmpty(path) && new File(path).isFile();
    }
}
