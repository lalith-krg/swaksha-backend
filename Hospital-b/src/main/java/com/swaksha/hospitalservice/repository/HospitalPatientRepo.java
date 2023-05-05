package com.swaksha.hospitalservice.repository;

import com.swaksha.hospitalservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalPatientRepo extends JpaRepository<Patient, String> {
}
