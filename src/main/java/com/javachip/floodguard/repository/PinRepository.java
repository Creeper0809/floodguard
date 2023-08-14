package com.javachip.floodguard.repository;

import com.javachip.floodguard.entity.Pin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PinRepository extends JpaRepository<Pin, Long> {
    List<Pin> findAllByalertpos(String pos);
    boolean existsBypos(String name);
}
