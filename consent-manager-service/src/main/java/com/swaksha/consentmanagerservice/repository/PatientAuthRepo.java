package com.swaksha.consentmanagerservice.repository;

import com.swaksha.consentmanagerservice.entity.PatientAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientAuthRepo extends JpaRepository<PatientAuth, String> {

//    @Modifying
//    @Query(value = "UPDATE Patient_Auth p SET" +
//            "p.patientSSID = :phoneNum" +
//            "p.authpin = :pin" +
//            "WHERE p.ssid LIKE :patientSSID",
//            nativeQuery = true)

//    int updatePin(@Param("patientSSID") String patientSSID, @Param("phoneNum") String phoneNum,
//                  @Param("pin") String pin);

    List<PatientAuth> findBySsid(String ssid);

}
