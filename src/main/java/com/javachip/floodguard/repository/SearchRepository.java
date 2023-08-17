package com.javachip.floodguard.repository;

import com.javachip.floodguard.entity.Pin;
import com.javachip.floodguard.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {
    @Query(value = "SELECT * FROM search WHERE ABS(TIMESTAMPDIFF(HOUR, :time ,searchdate)) <= 1 AND search_val = :val",nativeQuery = true)
    List<Search> findAllByHourTimeAndVal(LocalDateTime time,String val);
    @Query(value = "SELECT * FROM search WHERE ABS(TIMESTAMPDIFF(DAY, :time ,searchdate)) <= 1 AND search_val = :val",nativeQuery = true)
    List<Search> findAllByDayTimeAndVal(LocalDateTime time,String val);
}
