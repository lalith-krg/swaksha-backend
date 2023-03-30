package com.swaksha.consentmanagerservice.repository;

import com.swaksha.consentmanagerservice.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsentRepo extends JpaRepository<Consent, String> {

//    @Query("SELECT c from Consents c WHERE c.consentID = :consentID")
//    Consent searchConsentByID(String consentID);
//    Consent searchConsentByID(@Param("consentID") String consentID);

//    @Query("SELECT c from Consents c WHERE c.patientSSID = :ssid")
//    List<Consent> searchConsentBySSID(String ssid);
//    List<Consent> searchConsentBySSID(@Param("ssid") String ssid);

    List<Consent> findByConsentID(String consentID);
    List<Consent> findByPatientSSID(String patientSSID);
}
