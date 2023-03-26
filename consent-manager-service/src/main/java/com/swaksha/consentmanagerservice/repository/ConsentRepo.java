package com.swaksha.consentmanagerservice.repository;

import com.swaksha.consentmanagerservice.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsentRepo extends JpaRepository<Consent, String> {

    @Query("SELECT c from Consents c WHERE c.consentID = :consentID")
    Consent searchConsentByID(String consentID);

    @Query("SELECT c from Consents c WHERE c.patientSSID = :ssid")
    List<Consent> searchConsentBySSID(String ssid);
}
