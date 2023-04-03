package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.Patient;
import com.swaksha.gatewayservice.entity.PatientCred;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientCredRepo extends JpaRepository<PatientCred, String> {
    Optional<PatientCred> findBySsid(String Ssid);
}
