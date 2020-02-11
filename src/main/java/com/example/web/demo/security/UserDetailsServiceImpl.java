package com.example.web.demo.security;

import com.example.web.demo.model.User;
import com.example.web.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDetailsServiceImpl implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        String userIp = token.getPrincipal().toString();
        logger.info(userIp);

        User user = userRepository.findByUserIp(userIp);

        if (user == null) {
            user = new User(userIp);
            try {
                userRepository.save(user);
                logger.info(String.format("Created new user: %s", userIp));
            } catch (Exception ignored) {
            }
        }
        return new UserDetailsImpl(user);

    }
}
