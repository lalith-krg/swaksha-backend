package com.swaksha.consentmanagerservice.repository;

import com.swaksha.consentmanagerservice.entity.PatientAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientAuthRepo extends JpaRepository<PatientAuth, String> {
//    @Query("SELECT authpin FROM Patient_Auth_Pin p where p.ssid LIKE :patientSSID")
//    String getPatientPin(String patientSSID);
//    String getPatientPin(@Param("patientSSID") String patientSSID);

    @Modifying
    @Query(value = "UPDATE Patient_Auth_Pin p SET p.authpin = :newpin" +
            "WHERE p.ssid LIKE :patientSSID",
            nativeQuery = true)
//    int updatePin(String patientSSID, String newpin);
    int updatePin(@Param("patientSSID") String patientSSID, @Param("newpin") String newpin);

    List<PatientAuth> findBySsid(String ssid);

}
