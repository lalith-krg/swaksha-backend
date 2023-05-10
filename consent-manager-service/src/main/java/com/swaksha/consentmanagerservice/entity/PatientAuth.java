package com.swaksha.consentmanagerservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Patient_Auth")

public class PatientAuth {
    @Id
    private String ssid;

    @Column
    private String phoneNum;

    @Column
    private String authPin;
}