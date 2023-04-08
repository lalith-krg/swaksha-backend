package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, String> {
    List<Patient> findBySsid(String ssid);
}
