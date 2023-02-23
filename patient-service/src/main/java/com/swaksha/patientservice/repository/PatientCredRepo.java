package com.swaksha.patientservice.repository;

import com.swaksha.patientservice.entity.Patient;
import com.swaksha.patientservice.entity.PatientCred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientCredRepo extends JpaRepository<PatientCred, String> {
    public PatientCred findByUsername(String username);

//    public PatientCred findBySSID(String ssID);
//
//    public PatientCred findByPhoneNum(String phoneNum);
}
