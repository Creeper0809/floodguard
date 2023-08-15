package com.javachip.floodguard.repository;

import com.javachip.floodguard.entity.WhiteList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WhiteListRepository extends JpaRepository<WhiteList,Long> {
    WhiteList findByUserid(String userid);
}
