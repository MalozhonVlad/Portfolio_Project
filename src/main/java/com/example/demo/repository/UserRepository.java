package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends CrudRepository<User, Long> {

    UserDetails findByUsername(String username);

}
