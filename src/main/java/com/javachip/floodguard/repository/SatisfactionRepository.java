package com.javachip.floodguard.repository;

import com.javachip.floodguard.entity.Satisfaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SatisfactionRepository extends JpaRepository<Satisfaction, Long> {
}