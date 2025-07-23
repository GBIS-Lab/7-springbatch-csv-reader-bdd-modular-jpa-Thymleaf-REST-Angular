package com.smartphonebatch.config;

import com.smartphonebatch.model.Smartphone;
import jakarta.annotation.PostConstruct;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.smartphonebatch.writer.SmartphoneItemWriter;
import com.smartphonebatch.reader.SmartphoneItemReader;
import com.smartphonebatch.processor.SmartphoneItemProcessor;


import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final SmartphoneItemReader reader;
    private final SmartphoneItemProcessor processor;
    private final SmartphoneItemWriter writer;

    public BatchConfig(SmartphoneItemReader reader,
                       SmartphoneItemProcessor processor,
                       SmartphoneItemWriter writer) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @PostConstruct
    public void debug() {
        System.out.println("== Beans init ==");
        System.out.println("reader: " + reader);
        System.out.println("processor: " + processor);
        System.out.println("writer: " + writer);
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws IOException {
        return new StepBuilder("step1", jobRepository)
                .<Smartphone, Smartphone>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                //.writer(items -> {
                //	System.out.println("== Résultat du traitement ==");
                //	items.forEach(System.out::println);
                //})
                .writer(writer) // <-- Ici le JdbcBatchItemWriter
                .build();
    }

    @Bean
    public Job smartphoneJob(JobRepository jobRepository,
                   Step step) {
        return new JobBuilder("smartphoneJob", jobRepository)
                .start(step)
                .build();
    }
}