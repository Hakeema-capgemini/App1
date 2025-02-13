package com.example.project.tasklet;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.vo.UserVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class CsvToH2Tasklet {
	 private final JobRepository jobRepository;
	    private final PlatformTransactionManager transactionManager;

	    @PersistenceContext
	    private EntityManager entityManager;

	    public CsvToH2Tasklet(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
	        this.jobRepository = jobRepository;
	        this.transactionManager = transactionManager;
	    }

	    // Shared data storage for steps
	    private static final List<UserVO> userData = new ArrayList<>();

	    /**
	     * Tasklet for reading data from the CSV file.
	     */
	    @Bean
	    public Tasklet itemReaderTasklet() {
	        return (contribution, chunkContext) -> {
	            Files.lines(Paths.get("output/users.csv"))
	                    .skip(1) // Skip header row
	                    .map(line -> {
	                        String[] fields = line.split(",");
	                        UserVO user = new UserVO();
	                        user.setName(fields[1].trim());
	                        user.setAge(Integer.parseInt(fields[2].trim()));
	                        return user;
	                    })
	                    .forEach(userData::add);
	            System.out.println("Read Data: " + userData);
	            return RepeatStatus.FINISHED;
	        };
	    }

	    /**
	     * Tasklet for processing the data (e.g., transforming names).
	     */
	    @Bean
	    public Tasklet itemProcessorTasklet() {
	        return (contribution, chunkContext) -> {
	            userData.forEach(user -> user.setName(user.getName()));
	            System.out.println("Processed Data: " + userData);
	            return RepeatStatus.FINISHED;
	        };
	    }

	    /**
	     * Tasklet for writing the data to the H2 database.
	     */
	    @Bean
	    @Transactional
	    public Tasklet itemWriterTasklet() {
	        return (contribution, chunkContext) -> {
	            userData.forEach(entityManager::persist);
	            System.out.println("Data written to H2 DB");
	            return RepeatStatus.FINISHED;
	        };
	    }

	    /**
	     * Define the reader step.
	     */
	    @Bean
	    public Step readerStep() {
	        return new StepBuilder("readerStep", jobRepository)
	                .tasklet(itemReaderTasklet(), transactionManager)
	                .build();
	    }

	    /**
	     * Define the processor step.
	     */
	    @Bean
	    public Step processorStep() {
	        return new StepBuilder("processorStep", jobRepository)
	                .tasklet(itemProcessorTasklet(), transactionManager)
	                .build();
	    }

	    /**
	     * Define the writer step.
	     */
	    @Bean
	    public Step writerStep() {
	        return new StepBuilder("writerStep", jobRepository)
	                .tasklet(itemWriterTasklet(), transactionManager)
	                .build();
	    }

	    /**
	     * Define the job.
	     */
	    @Bean
	    public Job csvToH2TaskletJob() {
	        return new JobBuilder("csvToH2TaskletJob", jobRepository)
	                .start(readerStep())
	                .next(processorStep())
	                .next(writerStep())
	                .build();
	    }

}
