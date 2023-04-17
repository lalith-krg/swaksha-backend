package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.HospitalCred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalCredRepo extends JpaRepository<HospitalCred, String> {
}
