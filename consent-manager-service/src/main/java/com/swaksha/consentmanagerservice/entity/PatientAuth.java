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
    private String ssid;

    @Column
    private String phoneNum;

    @Column
    private String authPin;
}
