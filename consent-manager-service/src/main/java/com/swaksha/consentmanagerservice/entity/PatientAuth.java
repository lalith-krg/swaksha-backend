package com.swaksha.consentmanagerservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Patient_Auth_Pin")

public class PatientAuth {
    @Id
    @Column(unique = true, nullable = false)
    private String ssid;

    @Column(unique = true, nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private String authPin;
}
