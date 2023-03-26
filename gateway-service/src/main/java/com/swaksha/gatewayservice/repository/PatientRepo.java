package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient, String> {
}
