package com.example.web.demo.batch;

import com.example.web.demo.model.Customer;
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

public class CustomerTaskletProcess implements Tasklet, StepExecutionListener {
    private final Logger logger = LoggerFactory.getLogger(CustomerTaskletProcess.class);

    private List<Customer> customers;

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
        logger.info("afterStep ended.");
        return ExitStatus.COMPLETED;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        customers.forEach(c -> {
            c.setLastName("Nier");
        });
        return RepeatStatus.FINISHED;
    }
}
