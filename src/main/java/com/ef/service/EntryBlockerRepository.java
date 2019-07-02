package com.ef.service;

import com.ef.model.BlockedEntry;
import com.ef.model.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author bashir
 * @since 2/7/19.
 */
@Repository
public class EntryBlockerRepository {

    private static final Logger log = LoggerFactory.getLogger(EntryBlockerRepository.class);

    private static final String ENTRY_COUNT_QUERY = "SELECT ip, COUNT(id) c FROM log_entry le" +
            " WHERE le.entry_date >= ?" +
            " AND le.entry_date < ? GROUP BY ip HAVING c > ?";

    private static final String BLOCK_INSERT_QUERY = "INSERT INTO" +
            " black_list (ip, start_date, frequency, threshold, request_count, comments)" +
            " VALUES (?, ?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BlockedEntry> findIpAboveRequestThreshold(LocalDateTime start, Duration duration, int threshold) {
        log.debug("[EntryBlockerRepository::findIpAboveRequestThreshold] params" +
                " start {}, duration {}, threshold {}", start, duration.name(), threshold);

        List<BlockedEntry> thresholdExceedingEntryList = jdbcTemplate.query(ENTRY_COUNT_QUERY, new Object[]{
                start, duration.getEndTimeFor(start), threshold
        }, (rs, rowNum) -> new BlockedEntry(rs.getString("ip"), start, duration, threshold, rs.getInt("c")));

        log.debug("[EntryBlockerRepository::findIpAboveRequestThreshold] thresholdExceedingEntryList {}",
                thresholdExceedingEntryList.size());

        return thresholdExceedingEntryList;
    }

    @Transactional
    public int[] saveBlockedEntry(final List<BlockedEntry> blockedEntries) {
        log.debug("[EntryBlockerRepository::saveBlockedEntry] blockedEntries size {}",
                blockedEntries.size());

        int[] result = jdbcTemplate.batchUpdate(BLOCK_INSERT_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BlockedEntry blockedEntry = blockedEntries.get(i);
                ps.setString(1, blockedEntry.getIp());
                ps.setTimestamp(2, Timestamp.valueOf(blockedEntry.getStartDate()));
                ps.setString(3, blockedEntry.getDuration().name());
                ps.setInt(4, blockedEntry.getThreshold());
                ps.setInt(5, blockedEntry.getRequestCount());
                ps.setString(6, blockedEntry.getComments());
            }

            @Override
            public int getBatchSize() {
                return blockedEntries.size();
            }
        });

        log.debug("[EntryBlockerRepository::saveBlockedEntry] result {}", Arrays.toString(result));

        return result;
    }
}
