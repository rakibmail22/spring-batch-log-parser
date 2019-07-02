package com.ef;

import com.ef.config.RunParamConfig;
import com.ef.utils.StopWatch;
import com.ef.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.util.Arrays;

/**
 * @author bashir
 * @since 30/6/19.
 */
public class Parser {

    private static final Logger log = LoggerFactory.getLogger(Parser.class);

    public static void main(String[] args) throws Exception {
        Parser parserApp = new Parser();

        log.debug("[Parser::main] App Arguments: {}", Arrays.toString(args));

        parserApp.run(args);
    }

    public void run(String... args) throws Exception {
        AnnotationConfigApplicationContext ctx = createAndInitContext(args);

        RunParamConfig runParamConfig = ctx.getBean(RunParamConfig.class);

        if (runParamConfig.isValid()) {
            StopWatch stopWatch = StopWatch.start();
            JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
            Job job = ctx.getBean(Job.class);

            jobLauncher.run(job, new JobParameters());

            log.debug("[Parser::run] Job Execution Time: {} ms", stopWatch.elapsedTime());
        } else {
            System.out.println(Utils.INVALID_ARG_STR);
        }

        ctx.close();
    }

    private static AnnotationConfigApplicationContext createAndInitContext(String... args) {
        StopWatch stopWatch = StopWatch.start();
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        CommandLinePropertySource commandLinePropertySource = new SimpleCommandLinePropertySource(args);

        ctx.getEnvironment().getPropertySources().addFirst(commandLinePropertySource);
        ctx.scan("com.ef");
        ctx.refresh();

        log.debug("[Parser::createAndInitContext] Context Initialization Time: {} ms", stopWatch.elapsedTime());

        return ctx;
    }
}
