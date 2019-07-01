package com.ef.config;

import com.ef.model.LogEntry;
import com.ef.service.JobCompletionNotificationListener;
import com.ef.service.LogItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author bashir
 * @since 1/7/19.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<LogEntry> reader() {
        return new FlatFileItemReaderBuilder<LogEntry>()
                .name("logItemReader")
                .resource(new ClassPathResource("access.log"))
                .delimited()
                .delimiter("|")
                .names(new String[]{"dateStr", "ip", "request", "status", "userAgent"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<LogEntry>() {{
                    setTargetType(LogEntry.class);
                }})
                .build();
    }

    @Bean
    public LogItemProcessor processor() {
        return new LogItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<LogEntry> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<LogEntry>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO log_entry (entry_date, ip, request, status, user_agent)" +
                        " VALUES (:entryDate, :ip, :request, :status, :userAgent)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importEntryJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<LogEntry> writer) {
        return stepBuilderFactory.get("step1")
                .<LogEntry, LogEntry>chunk(10000)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Override
    public void setDataSource(DataSource dataSource) {
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
