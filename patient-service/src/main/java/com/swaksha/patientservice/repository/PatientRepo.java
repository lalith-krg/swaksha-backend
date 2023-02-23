package com.swaksha.patientservice.repository;

import com.swaksha.patientservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient, String> {
}
