package com.swaksha.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="Ehr")

public class Ehr {

    @Id
    String EhrId;

    @Column
    LocalDate creationDate;

    @ManyToOne
    Patient patient;

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
