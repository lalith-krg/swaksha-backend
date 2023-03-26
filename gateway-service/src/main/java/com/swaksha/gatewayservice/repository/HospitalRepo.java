package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepo extends JpaRepository<Hospital, String> {
}
