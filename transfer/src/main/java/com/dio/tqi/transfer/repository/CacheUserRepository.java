package com.dio.tqi.transfer.repository;

import com.dio.tqi.transfer.model.CacheUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CacheUserRepository extends JpaRepository<CacheUser, Long> {
    Optional<CacheUser> findByPixKey(String key);
}
