package com.swaksha.consentmanagerservice.repository;

import com.swaksha.consentmanagerservice.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsentRepo extends JpaRepository<Consent, String> {

    List<Consent> findByConsentID(String consentID);

    List<Consent> findByPatientSSID(String patientSSID);

//    @Modifying
//    @Query(value = "UPDATE consents c SET c. = :newpin" +
//            "WHERE p.ssid LIKE :patientSSID",
//            nativeQuery = true)
//
//    int updateConsent();
}
