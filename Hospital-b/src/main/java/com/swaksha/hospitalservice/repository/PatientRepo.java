package com.swaksha.hospitalservice.repository;

import com.swaksha.hospitalservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient,String> {
    Patient findPatientBySsid(String ssid);
}
