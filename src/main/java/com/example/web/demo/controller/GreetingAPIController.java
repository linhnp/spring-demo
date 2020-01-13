package com.example.web.demo.controller;

import com.example.web.demo.model.Greeting;
import com.example.web.demo.multipledb.model.web.Customer;
import com.example.web.demo.multipledb.model.web1.User;
import com.example.web.demo.multipledb.repository.web.CustomerRepository;
import com.example.web.demo.multipledb.repository.web1.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class GreetingAPIController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(value = "/customers", method=GET)
    @ResponseBody
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @RequestMapping(value = "/users", method=GET)
    @ResponseBody
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(value = "/customers", method = POST)
    @ResponseBody
    public Customer post(@RequestBody Customer newCus){
        return customerRepository.save(newCus);
    }
}
