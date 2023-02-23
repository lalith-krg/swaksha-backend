package com.swaksha.patientservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PatientCred {

    @Id
    @Column(unique = true, nullable = false)
    private String ssID;

//    @Id
    @Column(unique = true, nullable = false)
    private String username;

//    @Id
    @Column(unique = true, nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private String password;
}
