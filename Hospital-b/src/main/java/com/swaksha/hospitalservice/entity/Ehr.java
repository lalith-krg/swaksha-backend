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
    private String EhrId;


    @ManyToOne
    private Patient patient;

    @Column
    private String data;

    @Column
    private LocalDate creationDate;
}
