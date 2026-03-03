package org.generation.italy.cinema.auth.service;

import org.springframework.stereotype.Service;
import org.generation.italy.cinema.model.repositories.abstractions.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserServiceDetails implements UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // nel tuo caso username = email
        return userRepository.findByEmailIgnoreCase((username)).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
