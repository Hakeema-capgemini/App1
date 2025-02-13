package com.example.project.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.project.vo.UserVO;

import jakarta.persistence.EntityManagerFactory;


@Configuration
public class CsvToH2Batch {
	    private final JobRepository jobRepository;
	    private final PlatformTransactionManager transactionManager;
	    private final EntityManagerFactory entityManagerFactory;

	    public CsvToH2Batch(JobRepository jobRepository, PlatformTransactionManager transactionManager, EntityManagerFactory entityManagerFactory) {
	        this.jobRepository = jobRepository;
	        this.transactionManager = transactionManager;
	        this.entityManagerFactory = entityManagerFactory;
	    }

	    // 1. Reader - Reads data from the CSV file
	    @Bean(name = "csvReader")
	    public FlatFileItemReader<UserVO> csvReader() {
	        FlatFileItemReader<UserVO> reader = new FlatFileItemReader<>();
	        reader.setResource(new FileSystemResource("output/users.csv")); // Update the path
	        reader.setLinesToSkip(1); // Skip header
	        reader.setLineMapper(new DefaultLineMapper<>() {{
	            setLineTokenizer(new DelimitedLineTokenizer() {{
	                setDelimiter(",");
	                setNames("id", "name", "age");
	            }});
	            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
	                setTargetType(UserVO.class);
	            }});
	        }});
	        return reader;
	    }

	    // 2. Processor - Transform the input if necessary
	    @Bean
	    public ItemProcessor<UserVO, UserVO> csvProcessor() {
	        return item -> item; 
	    }

	    // 3. Writer - Write to the H2 Database
	    @Bean(name = "csvJpaWriter")
	    public JpaItemWriter<UserVO> csvJpaWriter() {
	        JpaItemWriter<UserVO> writer = new JpaItemWriter<>();
	        writer.setEntityManagerFactory(entityManagerFactory); // Set EntityManagerFactory for database operations
	        return writer;
	    }

	    // 4. Step - Defines a chunk-oriented step
	    @Bean(name = "csvToH2Step")
	    public Step csvToH2Step() {
	        return new StepBuilder("csvToH2Step", jobRepository)
	                .<UserVO, UserVO>chunk(10, transactionManager) // Process 10 items at a time
	                .reader(csvReader())
	                .processor(csvProcessor())
	                .writer(csvJpaWriter())
	                .build();
	    }

	    // 5. Job - Assemble the step into a Job
	    @Bean(name = "csvToH2Job")
	    @Primary
	    public Job csvToH2Job() {
	        return new JobBuilder("csvToH2Job", jobRepository)
	                .start(csvToH2Step())
	                .build();
	    }
	}