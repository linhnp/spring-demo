package com.example.web.demo.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.concurrent.CompletableFuture;

public class JobResultListener implements JobExecutionListener {
    private Job job;
    private JobLauncher jobLauncher;

    public JobResultListener(Job job, final JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Called beforeJob().");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Called afterJob().");
        CompletableFuture.runAsync(()->{
            try {
                JobParameters jobParameters = new JobParametersBuilder()
                        .addString("fileName", "hahaha")
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters();

                jobLauncher.run(job, jobParameters);
            } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                    | JobParametersInvalidException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }
}
