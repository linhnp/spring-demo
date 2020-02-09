package com.example.web.demo.batch;

import com.example.web.demo.model.Customer;
import com.example.web.demo.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ABatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CustomerRepository customerRepository;

//     this one does not work well in this case, as the paging order is changing after each write
//    @Bean
//    public RepositoryItemReader<Customer> reader() {
//        Map<String, Sort.Direction> sort = new HashMap<>();
//        sort.put("id", Sort.Direction.ASC);
//        List<Object> paras = new ArrayList<>();
//        paras.add("Nier");
//        return new RepositoryItemReaderBuilder<Customer>()
//                .name("customerItemReader")
//                .repository(customerRepository)
//                .methodName("findByLastName")
//                .arguments(paras)
//                .sorts(sort)
//                .build();
//    }

    @Bean
    public ItemReader<Customer> reader() {
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        List<Object> paras = new ArrayList<>();
        paras.add("Nier");
        CustomerItemReader<Customer> customerItemReader = new CustomerItemReader<>();
        customerItemReader.setRepository(customerRepository);
        customerItemReader.setMethodName("findByLastName");
        customerItemReader.setArguments(paras);
        customerItemReader.setSort(sort);
        return customerItemReader;
    }

    @Bean
    public RepositoryItemWriter<Customer> writer() {
        return new RepositoryItemWriterBuilder<Customer>()
                .repository(customerRepository)
                .methodName("save")
                .build();
    }
//    @Bean
//    public ItemWriter<Customer> writer() {
//        return new CustomerItemWriter(customerRepository);
//    }

    @Bean
    public CustomItemProcessor processor() {
        return new CustomItemProcessor();
    }

    // batch
    @Bean(name = "batchJob")
    public Job job() {
        Step step = stepBuilderFactory.get("step1")
                .<Customer, Customer>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
        return jobBuilderFactory.get("firstBatchJob").start(step).build();
    }

    @Bean(name = "taskletJob")
    public Job job1() {
        Step read = stepBuilderFactory.get("taskletReader")
                .tasklet(new CustomerTaskletReader(customerRepository)).build();
        Step process = stepBuilderFactory.get("taskletProcess")
                .tasklet(new CustomerTaskletProcess()).build();
        Step write = stepBuilderFactory.get("taskletWrite")
                .tasklet(new CustomerTaskletWriter(customerRepository)).build();
        return jobBuilderFactory.get("secondBatchJob")
                .start(read).next(process).next(write).build();
    }

}
