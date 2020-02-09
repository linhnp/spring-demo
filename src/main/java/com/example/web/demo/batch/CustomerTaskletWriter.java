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
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

public class CustomerTaskletWriter implements Tasklet, StepExecutionListener {
    private final Logger logger = LoggerFactory.getLogger(CustomerTaskletWriter.class);

    private CustomerRepository customerRepository;

    private List<Customer> customers;

    public CustomerTaskletWriter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("beforeStep: get context");
        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();
        this.customers = (List<Customer>) executionContext.get("records");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("afterStep: end");
        return ExitStatus.COMPLETED;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        logger.info("execute: saveAll");
        customerRepository.saveAll(customers);

        return RepeatStatus.FINISHED;
    }
}
