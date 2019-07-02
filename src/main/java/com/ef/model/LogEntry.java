package com.ef.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author bashir
 * @since 1/7/19.
 */
public class LogEntry implements Serializable {

    private static final long serialVersionUID = 23L;

    private String dateStr;

    private LocalDateTime entryDate;

    private String ip;

    private String request;

    private String status;

    private String userAgent;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
