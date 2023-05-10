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
    private  Integer EhrId;

    @ManyToOne
    private Patient patient;

    @Column
    private String data;

    @Column
    private LocalDate creationDate;
    


}
