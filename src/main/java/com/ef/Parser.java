package com.ef;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.io.IOException;

/**
 * @author bashir
 * @since 30/6/19.
 */
public class Parser {

    public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, IOException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();


        CommandLinePropertySource commandLinePropertySource = new SimpleCommandLinePropertySource(args);
        ctx.getEnvironment().getPropertySources().addFirst(commandLinePropertySource);

        ctx.scan("com.ef");

        ctx.refresh();

        JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
        Job job = ctx.getBean(Job.class);

        jobLauncher.run(job, new JobParameters());

        ctx.close();
    }
}
