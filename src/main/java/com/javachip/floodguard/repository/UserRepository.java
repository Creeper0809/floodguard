package com.javachip.floodguard.repository;

import com.javachip.floodguard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserid(String userid);
    boolean existsByUsername(String username);
    Optional<User> findByUserid(String userid);

}
