package com.example.medcheckb7.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "experts")
public class Expert {

    @Id
    @SequenceGenerator(name = "expert_gen", sequenceName = "expert_seq", allocationSize = 1, initialValue = 23)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expert_gen")
    private Long id;

    private String expertFirstName;

    private String expertLastName;

    private String expertPosition;

    private Boolean expertStatus;
    @Column(length = 1000000)
    private String expertImage;

    @Column(length = 1000000)
    private String expertInformation;

    @Column(length = 1000000)
    private String expertTimeTable;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private ClinicService clinicService;

    @OneToOne(cascade = ALL, mappedBy = "expert")
    private Schedule schedule;

    @OneToMany(cascade = ALL, mappedBy = "expert")
    private List<OnlineEntry> onlineEntries;
}
