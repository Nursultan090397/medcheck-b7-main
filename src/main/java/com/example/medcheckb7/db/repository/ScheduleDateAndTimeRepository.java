package com.example.medcheckb7.db.repository;

import com.example.medcheckb7.db.entities.ScheduleDateAndTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleDateAndTimeRepository extends JpaRepository<ScheduleDateAndTime, Long> {
    @Query("select s from ScheduleDateAndTime as s where s.schedule.expert.id = :userId and s.date = :localDate")
    List<ScheduleDateAndTime> getAllEntry(Long userId, LocalDate localDate);

    @Modifying
    @Query("DELETE FROM ScheduleDateAndTime a WHERE a.id IN (:ids)")
    void deleteSelectedSchedules(@Param("ids") List<Long> ids);
}