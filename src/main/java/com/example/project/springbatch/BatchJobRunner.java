package com.example.project.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class BatchJobRunner {
	    @Autowired
	    private JobLauncher jobLauncher;
    
	    @Autowired
	    private Job csvToH2Job;

	    public void run(String... args) throws Exception {
	        // Specify which job to run
	        JobExecution jobExecution = jobLauncher.run(csvToH2Job, new org.springframework.batch.core.JobParameters());
	        System.out.println("Job Execution Status: " + jobExecution.getStatus());     
	   }
}
