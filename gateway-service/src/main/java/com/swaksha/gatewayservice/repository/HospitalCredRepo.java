package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.HospitalCred;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalCredRepo extends JpaRepository<HospitalCred, String> {
}
