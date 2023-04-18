package com.example.medcheckb7.db.repository;

import com.example.medcheckb7.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.userEmail = :email")
    Optional<User> findByEmail(String email);

    @Query("select case when count(u)>0 then true else false end from User u where u.userEmail=:email")
    boolean existsByEmail(@Param(value = "email") String email);

    @Query("select e from User e where upper(e.userFirstName) like concat(:text, '%')  or upper(e.userLastName) like concat(:text, '%') ")
    List<User> searchUser(@Param("text") String text);
}