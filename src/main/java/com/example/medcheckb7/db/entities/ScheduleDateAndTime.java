package com.example.medcheckb7.db.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "schedule_date_and_time")
public class ScheduleDateAndTime {
    @Id
    @SequenceGenerator(name = "schedule_date_and_time_gen", sequenceName = "schedule_date_and_time_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_date_and_time_gen")
    private Long id;

    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime finishTime;

    private Boolean timeStatus;

    @ManyToOne(cascade = {REFRESH,MERGE,DETACH,PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public ScheduleDateAndTime(LocalDate date, LocalTime startTime, LocalTime finishTime, Boolean timeStatus, Schedule schedule) {
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.timeStatus = timeStatus;
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDateAndTime that = (ScheduleDateAndTime) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
