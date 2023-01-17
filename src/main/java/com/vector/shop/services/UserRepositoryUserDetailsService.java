package com.vector.shop.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vector.shop.user.User;
import com.vector.shop.user.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;
    public UserRepositoryUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user!=null) return user;
        throw new UsernameNotFoundException(String.format("%s : No such username%n", username));
    }
    
}
