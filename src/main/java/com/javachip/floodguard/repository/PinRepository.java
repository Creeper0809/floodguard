package com.javachip.floodguard.repository;

import com.javachip.floodguard.entity.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PinRepository extends JpaRepository<Pin, Long> {
    List<Pin> findAllByalertpos(String pos);
    @Query(value = "SELECT * FROM pin WHERE userid = \"root\" or userid = :userid",nativeQuery = true)
    List<Pin> findAllByuserid(String userid);
    boolean existsBypos(String name);
}
