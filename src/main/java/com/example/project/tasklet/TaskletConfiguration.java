//package com.example.project.tasklet;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import com.example.project.vo.UserVO;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//
//@Configuration
//public class TaskletConfiguration {
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final EntityManagerFactory entityManagerFactory;
//
//    public TaskletConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager, EntityManagerFactory entityManagerFactory) {
//        this.jobRepository = jobRepository;
//        this.transactionManager = transactionManager;
//        this.entityManagerFactory = entityManagerFactory;
//    }
//
//    // 1. Tasklet for Reading Data from H2 Database
//    @Bean
//    public Tasklet dbReaderTasklet() {
//        return (stepContribution, chunkContext) -> {
//            System.out.println("Reading data from H2 database...");
//
//            EntityManager entityManager = entityManagerFactory.createEntityManager();
//            List<UserVO> students = entityManager
//                    .createQuery("SELECT u FROM UserVO u", UserVO.class)
//                    .getResultList();
//            stepContribution.getStepExecution().getJobExecution().getExecutionContext()
//            .put("students", students);
//                
//                System.out.println("Students fetched: " + students);
//                return RepeatStatus.FINISHED;
//        };
//    }
//
//
//    // 2. Tasklet for Processing Data
//    @Bean
//    public Tasklet dbProcessorTasklet() {
//        return (stepContribution, chunkContext) -> {
//            System.out.println("Processing data...");
//
//            List<UserVO> students = (List<UserVO>) chunkContext.getStepContext().getStepExecution().getExecutionContext().get("data");
//            if (students == null) {
//                throw new IllegalStateException("No data available to process");
//            }
//
//            List<String> processedData = students.stream()
//                    .map(user -> user.getId() + "," + user.getName() + "," + user.getAge())
//                    .collect(Collectors.toList());
//
//            chunkContext.getStepContext().getStepExecution().getExecutionContext().put("processedData", processedData);
//
//            processedData.forEach(System.out::println);
//            return RepeatStatus.FINISHED;
//        };
//    }
//
//    // 3. Tasklet for Writing Data to a CSV File
//    @Bean
//    public Tasklet dbWriterTasklet() {
//        return (stepContribution, chunkContext) -> {
//            System.out.println("Writing data to CSV file...");
//
//            List<String> processedData = (List<String>) chunkContext.getStepContext().getStepExecution().getExecutionContext().get("processedData");
//            if (processedData == null || processedData.isEmpty()) {
//                throw new IllegalStateException("No processed data available to write");
//            }
//
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/tasklet.csv"))) {
//                writer.write("id,name,age");
//                writer.newLine();
//                for (String line : processedData) {
//                    writer.write(line);
//                    writer.newLine();
//                }
//            }
//
//            System.out.println("Data successfully written to output.csv!");
//            return RepeatStatus.FINISHED;
//        };
//    }
//
//    // 4. Steps
//    @Bean
//    public Step readerStep() {
//        return new StepBuilder("readerStep", jobRepository)
//                .tasklet(dbReaderTasklet(), transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step processorStep() {
//        return new StepBuilder("processorStep", jobRepository)
//                .tasklet(dbProcessorTasklet(), transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step writerStep() {
//        return new StepBuilder("writerStep", jobRepository)
//                .tasklet(dbWriterTasklet(), transactionManager)
//                .build();
//    }
//
//    // 5. Job
//    @Bean
//    public Job taskletJob() {
//        return new JobBuilder("taskletJob", jobRepository)
//                .start(readerStep())
//                .next(processorStep())
//                .next(writerStep())
//                .build();
//    }
//}
//
////	
////	 @Bean
////	    public Tasklet itemReaderTasklet() {
////			List<String> data = new ArrayList<>();
////	        return (contribution, chunkContext) -> {
////	        	data.add("Virat, 25");
////	            data.add("Rohit, 30");
////	            data.add("Bhumrah, 35");
////	            System.out.println("Data read: " + data);
////	            return RepeatStatus.FINISHED;
////	        };
////	    }
////
////	    @Bean
////	    public Tasklet itemProcessorTasklet() {
////	        return (contribution, chunkContext) -> {
////	            System.out.println("Processing items...");
////	            ExecutionContext context = chunkContext.getStepContext().getStepExecution()
////	                                                    .getJobExecution().getExecutionContext();
////	            List<String> data = (List<String>) context.get("data");
////
////	            List<String> processedData = data.stream()
////	                                             .map(String::toUpperCase)
////	                                             .toList();
////	            context.put("processedData", processedData);
////	            return RepeatStatus.FINISHED;
////	        };
////	    }
////
////	    @Bean
////	    public Tasklet itemWriterTasklet() {
////	        return (contribution, chunkContext) -> {
////	            System.out.println("Writing items...");
////	            ExecutionContext context = chunkContext.getStepContext().getStepExecution()
////	                                                    .getJobExecution().getExecutionContext();
////	            List<String> processedData = (List<String>) context.get("processedData");
////
////	            processedData.forEach(System.out::println);
////	            return RepeatStatus.FINISHED;
////	        };
////	    }
////
////	    @Bean
////	    @Primary
////	    public Step itemReaderStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
////	        return new StepBuilder("itemReaderStep", jobRepository)
////	                .tasklet(itemReaderTasklet(), transactionManager)
////	                .build();
////	    }
////
////	    @Bean
////	    public Step itemProcessorStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
////	        return new StepBuilder("itemProcessorStep", jobRepository)
////	                .tasklet(itemProcessorTasklet(), transactionManager)
////	                .build();
////	    }
////
////	    @Bean
////	    public Step itemWriterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
////	        return new StepBuilder("itemWriterStep", jobRepository)
////	                .tasklet(itemWriterTasklet(), transactionManager)
////	                .build();
////	    }
////
////	    @Bean
////	    public Job batchJob(
////	            JobRepository jobRepository,
////	            @Qualifier("itemReaderStep") Step itemReaderStep,
////	            @Qualifier("itemProcessorStep") Step itemProcessorStep,
////	            @Qualifier("itemWriterStep") Step itemWriterStep) {
////	        return new JobBuilder("batchJob", jobRepository)
////	                .start(itemReaderStep)
////	                .next(itemProcessorStep)
////	                .next(itemWriterStep)
////	                .build();
////	    }
////
////}
