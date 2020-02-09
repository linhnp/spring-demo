package com.example.web.demo.batch;

import com.example.web.demo.model.Customer;
import com.example.web.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class CustomerItemWriter implements ItemWriter<Customer> {
    Logger logger = LoggerFactory.getLogger(CustomItemProcessor.class);
    private CustomerRepository customerRepository;

    public CustomerItemWriter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void write(List<? extends Customer> items) throws Exception {
        logger.info("save them");
        customerRepository.saveAll(items);
    }
}
