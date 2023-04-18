package com.example.medcheckb7.db.repository;

import com.example.medcheckb7.db.entities.ClinicService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicServiceRepository extends JpaRepository<ClinicService, Long> {
}
