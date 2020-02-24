package com.example.web.demo.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JobLauncherController {
    @Autowired
    JobLauncher jobLauncher;

//    @Autowired
//    @Qualifier("batchJob")
//    Job job1;

    @Autowired
    @Qualifier("taskletJob")
    Job job2;

//    @RequestMapping("/jobLauncher.html")
//    public void handle() throws Exception {
//        // need this to rerun the same job
//        JobParameters jobParameters =
//                new JobParametersBuilder()
//                        .addLong("time", System.currentTimeMillis()).toJobParameters();
//        jobLauncher.run(job1, jobParameters);
//    }

    @RequestMapping("/jobLauncher1.html")
    public void handle1() throws Exception {
        // need this to rerun the same job
        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(job2, jobParameters);
    }
}
