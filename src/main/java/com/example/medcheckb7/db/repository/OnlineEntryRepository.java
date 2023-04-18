package com.example.medcheckb7.db.repository;

import com.example.medcheckb7.db.entities.OnlineEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface OnlineEntryRepository extends JpaRepository<OnlineEntry, Long> {

    @Query("SELECT e FROM OnlineEntry e WHERE e.isDeleted = true")
    List<OnlineEntry> findDeletedEntries();

    @Query("select a from OnlineEntry a where upper(a.userName) like concat(:text, '%')")
    List<OnlineEntry> searchAppointments(@Param("text") String text);

    @Query("SELECT oe FROM OnlineEntry oe WHERE oe.user.id = :userId")
    List<OnlineEntry> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("delete from OnlineEntry oe WHERE oe.user.id = :userId")
    void deleteOnlineEntriesBy(Long userId);

}