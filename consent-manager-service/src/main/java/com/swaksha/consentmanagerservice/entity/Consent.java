package com.swaksha.consentmanagerservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Consents")

public class Consent {
    @Id
    private String consentID;

    @Column
    private LocalDateTime consentEndTime;

    @Column
    private boolean isApproved;

    @Column
    private boolean selfConsent;
    @Column
    private String doctorSSID;
    @Column
    private String hiuSSID;

    @Column
    private String patientSSID;

    @Column
    private String hipSSID;

    @Column
    private LocalDateTime dataAccessStartTime;

    @Column
    private LocalDateTime dataAccessEndTime;

    @Column
    private LocalDateTime requestInitiatedTime;

    @Column
    private LocalDateTime consentApprovedTime;
}
