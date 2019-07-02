package com.ef.service;

import com.ef.config.RunParamConfig;
import com.ef.model.BlockedEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author bashir
 * @since 1/7/19.
 */
@Component
public class LogImportCompletionListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(LogImportCompletionListener.class);

    @Autowired
    private EntryBlockerRepository entryBlockerRepository;

    @Autowired
    private RunParamConfig runParamConfig;

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.debug("[LogImportCompletionListener:: afterJob] jobExecutionStatus {}", jobExecution.getStatus());

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            List<BlockedEntry> toBeBlocked = entryBlockerRepository.findIpAboveRequestThreshold(runParamConfig.getStartDate(),
                    runParamConfig.getDuration(),
                    runParamConfig.getThreshold());

            toBeBlocked.forEach(be -> System.out.println(be.getIp()));

            entryBlockerRepository.saveBlockedEntry(toBeBlocked);
        }
    }
}