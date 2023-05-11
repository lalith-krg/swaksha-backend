package com.swaksha.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="Ehr")

public class Ehr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer EhrId;

    @Column
    private LocalDate creationDate;

    @ManyToOne
    private Patient patient;

//    @Column
//    String data;

    @Column
    String type;


    // EHR of type "observation"
    @Column
    String observationCode;
    @Column
    String observationValue;


    // EHR of type "condition"
    @Column
    String conditionCode;

    // EHR of type "procedure"
    @Column
    String procedureCode;
}
