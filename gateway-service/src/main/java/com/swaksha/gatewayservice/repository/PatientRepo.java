package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepo extends JpaRepository<Patient, String> {
}
