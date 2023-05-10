package com.swaksha.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

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


    @ManyToOne
    Patient patient;

    @Column
    String data;
    

}
