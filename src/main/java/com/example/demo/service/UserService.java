package com.example.demo.service;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {

        User createNewUser = new User();

        createNewUser.setUsername(user.getUsername());
        createNewUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        createNewUser.setEmail(user.getEmail());

        createNewUser.setAccountNonExpired(true);
        createNewUser.setCredentialsNonExpired(true);
        createNewUser.setAccountNonLocked(true);
        createNewUser.setEnabled(true);
        createNewUser.setAuthorities(Collections.singleton(Role.USER));

        userRepository.save(createNewUser);
    }


    public User findByUsername(String username) {
        return (User)userRepository.findByUsername(username);
    }
}
