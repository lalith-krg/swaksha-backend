package com.swaksha.gatewayservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Patient_Credentials")

public class PatientCred {

    @Id
    @Column(unique = true, nullable = false)
    private String ssid;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "ssid", referencedColumnName = "ssid")
    private Patient patient;
}
