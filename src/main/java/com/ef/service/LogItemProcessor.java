package com.ef.service;

import com.ef.model.LogEntry;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bashir
 * @since 1/7/19.
 */
public class LogItemProcessor implements ItemProcessor<LogEntry, LogEntry> {
    @Override
    public LogEntry process(LogEntry item) throws Exception {

        LogEntry entry = new LogEntry();
        entry.setDateStr(item.getDateStr());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        entry.setEntryDate(LocalDateTime.parse(item.getDateStr(), formatter));

        entry.setIp(item.getIp());
        entry.setRequest(item.getRequest());
        entry.setStatus(item.getStatus());
        entry.setUserAgent(item.getUserAgent());

        return entry;
    }
}
