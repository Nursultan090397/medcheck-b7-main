package com.example.medcheckb7.db.repository;

import com.example.medcheckb7.db.entities.ClinicService;
import com.example.medcheckb7.db.entities.Expert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    @Query("select e from Expert e where upper(e.expertFirstName) like concat(:text, '%')")
    List<Expert> searchExpert(@Param("text") String text, Pageable pageable);

    @Query("SELECT e FROM Expert e WHERE e.clinicService = :clinicService")
    List<Expert> findByClinicService(@Param("clinicService") ClinicService clinicService);

    @Query("select s from Expert s where s.expertFirstName like concat(:searchText, '%')")
    List<Expert> search(String searchText);
}