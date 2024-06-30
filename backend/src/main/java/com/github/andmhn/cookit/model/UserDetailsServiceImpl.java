package com.github.andmhn.cookit.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user_query = userRepository.findByEmail(email);

        if (user_query.isPresent()) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user_query.get().getEmail())
                    .password(user_query.get().getPassword())
                    .build();
        } else {
            System.err.println("User: " + email + " not found!");
            throw new UsernameNotFoundException("User: " + email + " is not present!!!");
        }
    }

}
