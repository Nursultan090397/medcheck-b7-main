package com.example.medcheckb7.db.entities;

import com.example.medcheckb7.db.entities.enums.RepeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @SequenceGenerator(name = "schedule_gen", sequenceName = "schedule_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_gen")
    private Long id;

    private LocalDate startDate;

    private LocalDate finishDate;

    @OneToMany(cascade = ALL,mappedBy = "schedule",orphanRemoval = true)
    private List<ScheduleDateAndTime> dateAndTimes;

    private Long clockInterval;

    @ElementCollection
    private Map<String, Boolean> recurrenceDays;

    @Enumerated(value = EnumType.STRING)
    private RepeatType repeatType;

    @OneToOne(cascade = {DETACH, REFRESH, MERGE})
    private Expert expert;

    @OneToOne(cascade = {DETACH, REFRESH, MERGE})
    private ClinicService clinicService;
}
