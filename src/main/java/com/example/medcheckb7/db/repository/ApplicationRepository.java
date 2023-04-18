package com.example.medcheckb7.db.repository;

import com.example.medcheckb7.db.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select a from Application a where upper(a.firstName) like concat(:text, '%')")
    List<Application> searchApplication(@Param("text") String text);

    @Modifying
    @Query("DELETE FROM Application a WHERE a.id IN (:ids)")
    void deleteSelectedApplications(@Param("ids") List<Long> ids);
}