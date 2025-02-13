package com.example.project.springbatch;
	

import java.util.Collections;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.project.dao.UserRepository;
import com.example.project.vo.UserVO;
@Configuration
public class H2ToCsvBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final UserRepository userRepository;

    public H2ToCsvBatch(JobRepository jobRepository, PlatformTransactionManager transactionManager, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.userRepository = userRepository;
    }

    // 1. Reader - RepositoryItemReader to fetch data from the database
    @Bean
    public RepositoryItemReader<UserVO> reader() {
        RepositoryItemReader<UserVO> reader = new RepositoryItemReader<>();
        reader.setRepository(userRepository);
        reader.setMethodName("findAll"); // Calls UserRepository's findAll()
        reader.setPageSize(10);
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        return reader;
    }

    // 2. Processor - PassThroughItemProcessor 
    @Bean
    public PassThroughItemProcessor<UserVO> processor() {
        return new PassThroughItemProcessor<>();
    }

    // 3. Writer - FlatFileItemWriter to write data to a CSV file
    @Bean
    public FlatFileItemWriter<UserVO> writer() {
        return new FlatFileItemWriterBuilder<UserVO>()
                .name("csvWriter")
                .resource(new FileSystemResource("output/users.csv"))
                .delimited()
                .delimiter(",")
                .names("id", "name", "age")
                .headerCallback(writer -> writer.write("ID,Name,Age")) // Add CSV header
                .build();
    }

    // 4. Step - Defines a chunk-oriented step
    @Bean
    public Step step() {
        return new StepBuilder("step1", jobRepository)
                .<UserVO, UserVO>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    // 5. Job - Assembles the step into a Job
    @Bean
    @Qualifier
    public Job job() {
        return new JobBuilder("h2ToCsvJob", jobRepository)
                .start(step())
                .build();
    }
}
