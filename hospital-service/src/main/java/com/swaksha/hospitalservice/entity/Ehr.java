package com.swaksha.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

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
    Integer EhrId;

    @ManyToOne
    Patient patient;

    @Column
    String data;


}
