package com.example.medcheckb7.db.repository;

import com.example.medcheckb7.db.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query("SELECT r FROM Result r WHERE r.resultOrderNumber = :orderNumber")
    List<Result> findByOrderNumber(@Param("orderNumber") String orderNumber);
}

