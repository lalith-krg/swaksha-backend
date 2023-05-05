package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.HospitalUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalUrlRepo extends JpaRepository<HospitalUrl, String> {
    HospitalUrl findByHospitalSsid(String hipSsid);
}
