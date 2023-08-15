package com.javachip.floodguard.repository;

import com.javachip.floodguard.entity.Favorite;
import com.javachip.floodguard.entity.Pin;
import com.javachip.floodguard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllBypinid(Long pinid);
    List<Favorite> findAllByUserid(Long userid);
}
