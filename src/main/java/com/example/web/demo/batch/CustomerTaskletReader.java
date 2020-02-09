package com.example.web.demo.batch;

import com.example.web.demo.model.Customer;
import com.example.web.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

public class CustomerTaskletReader implements Tasklet, StepExecutionListener {
    private final Logger logger = LoggerFactory.getLogger(CustomerTaskletReader.class);

    private CustomerRepository customerRepository;

    private List<Customer> customers;

    public CustomerTaskletReader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("before Step: nothing here");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("afterStep: return");
        stepExecution
                .getJobExecution()
                .getExecutionContext()
                .put("records", customers);
        return ExitStatus.COMPLETED;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        logger.info("execute: findAll");
        customers = customerRepository.findAll();

        return RepeatStatus.FINISHED;
    }
}
