package com.javachip.floodguard.repository;

import com.javachip.floodguard.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListRepository extends JpaRepository<BlackList,Long> {
    Optional<BlackList> findByUserid(String userid);
}
