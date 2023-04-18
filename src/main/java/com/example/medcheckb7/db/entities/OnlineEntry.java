package com.example.medcheckb7.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "online_entries")
public class OnlineEntry {

    @Id
    @SequenceGenerator(name = "online_entry_gen", sequenceName = "online_entry_seq", allocationSize = 1, initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "online_entry_gen")
    private Long id;

    @Column(length = 10000)
    private String onlineEntryStatus;

    private String userName;

    private String userPhoneNumber;

    private String userEmail;

    private LocalTime recordedTime;

    private LocalDate recordedDate;

    private Boolean isDeleted;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Expert expert;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User user;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH})
    private ClinicService clinicService;

}
