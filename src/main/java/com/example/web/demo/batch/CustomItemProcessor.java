package com.example.web.demo.batch;

import com.example.web.demo.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Customer, Customer> {
    Logger logger = LoggerFactory.getLogger(CustomItemProcessor.class);

    @Override
    public Customer process(Customer item) throws Exception {
        item.setLastName("Automata");
        logger.info("hello from the other side");
        return item;
    }
}
