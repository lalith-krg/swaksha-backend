package com.swaksha.consentmanagerservice.consents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swaksha.consentmanagerservice.consents.entity.Consent;

import java.util.List;

@Repository
public interface ConsentRepo extends JpaRepository<Consent, String> {

    List<Consent> findByConsentID(String consentID);
    List<Consent> findByPatientSSID(String patientSSID);
}