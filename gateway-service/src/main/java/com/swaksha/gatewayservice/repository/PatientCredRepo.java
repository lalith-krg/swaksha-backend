package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.PatientCred;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientCredRepo extends JpaRepository<PatientCred, String> {
}
