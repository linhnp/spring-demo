package com.example.web.demo.security;

import com.example.web.demo.model.Role;
import com.example.web.demo.model.User;
import com.example.web.demo.repository.RoleRepository;
import com.example.web.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@ConditionalOnExpression("${app.init.db:false}")
public class SetupCommand implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Role admin = new Role("ADMIN");
        Role user = new Role("USER");

        roleRepository.save(admin);
        roleRepository.save(user);
        Set<Role> roles= new HashSet<>();

        roles.add(admin);
        roles.add(user);

        User local = new User("0:0:0:0:0:0:0:1");
        local.setRoles(roles);

        userRepository.save(local);

    }
}
