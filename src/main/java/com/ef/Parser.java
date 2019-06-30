package com.ef;

import com.ef.config.RunParamConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

/**
 * @author bashir
 * @since 30/6/19.
 */
public class Parser {

    private static final String QUERY = "SELECT name FROM tmp;";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        CommandLinePropertySource commandLinePropertySource = new SimpleCommandLinePropertySource(args);
        ctx.getEnvironment().getPropertySources().addFirst(commandLinePropertySource);
        ctx.scan("com.ef");

        ctx.refresh();

        RunParamConfig runParamConfig = ctx.getBean(RunParamConfig.class);
        System.out.println(runParamConfig.getThreshold());

        ctx.close();
    }
}
