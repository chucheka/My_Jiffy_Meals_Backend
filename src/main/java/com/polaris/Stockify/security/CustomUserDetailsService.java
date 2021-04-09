package com.polaris.Stockify.security;


import com.polaris.Stockify.model.User;
import com.polaris.Stockify.model.UserPrincipal;
import com.polaris.Stockify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
    	User user = userRepository.findByEmail(email)
    			.orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + email));
        return new UserPrincipal(user);
        
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
    	
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new UserPrincipal(user);
    }
    
}