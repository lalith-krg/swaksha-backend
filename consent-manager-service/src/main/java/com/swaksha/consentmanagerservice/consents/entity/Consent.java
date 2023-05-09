package com.swaksha.consentmanagerservice.consents.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
    private LocalDate consentEndDate;

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
    private LocalDate dataAccessStartDate;

    @Column
    private LocalDate dataAccessEndDate;

    @Column
    private LocalDate requestInitiatedDate;

    @Column
    private LocalDate consentApprovedDate;
}
