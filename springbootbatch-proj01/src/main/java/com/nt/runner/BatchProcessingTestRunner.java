package com.nt.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchProcessingTestRunner implements CommandLineRunner {
	@Autowired
	private JobLauncher launcher;

	@Autowired
	private Job job;

	@Override
	public void run(String... args) throws Exception {
		// prepare job parameters
		JobParameters params = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
		// run the job
		JobExecution execution = launcher.run(job, params);
		System.out.println("BatchProcessingTestRunner::" + execution.getStatus());
		System.out.println("Exit status::" + execution.getExitStatus());
		System.out.println("Job Id::" + execution.getJobId());
	}

}
