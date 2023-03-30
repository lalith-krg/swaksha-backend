package com.swaksha.consentmanagerservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Consents")

public class Consent {
    @Id
    private String consentID;

    @Column(nullable = false)
    private LocalDateTime consentEndTime;

    @Column(nullable = false)
    private boolean isApproved;

    @Column(nullable = false)
    private boolean selfConsent;
    @Column(nullable = false)
    private String doctorSSID;
    @Column(nullable = false)
    private String hiuSSID;

    @Column(nullable = false)
    private String patientSSID;

    private String hipSSID;

    private LocalDateTime dataAccessStartTime;

    private LocalDateTime dataAccessEndTime;

    @Column(nullable = false)
    private LocalDateTime requestInitiatedTime;

    private LocalDateTime consentApprovedTime;
}
