package com.javachip.floodguard.repository;

import com.javachip.floodguard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhonenumber(String phonenumber);
    Optional<User> findByEmail(String id);
    User findAllByUsername(String username);
    Optional<User> findByUsername(String username);

}
