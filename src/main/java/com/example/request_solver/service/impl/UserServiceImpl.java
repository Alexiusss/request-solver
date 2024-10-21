package com.example.request_solver.service.impl;

import com.example.request_solver.AuthUser;
import com.example.request_solver.model.Role;
import com.example.request_solver.model.User;
import com.example.request_solver.repository.UserRepository;
import com.example.request_solver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.request_solver.util.ValidationUtil.checkNotFoundWithName;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AuthUser(userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User '" + username + "' was not found")
                ));
    }
}