package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.Patient;
import com.swaksha.gatewayservice.entity.PatientCred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientCredRepo extends JpaRepository<PatientCred, String> {
    Optional<PatientCred> findBySsid(String Ssid);
}
