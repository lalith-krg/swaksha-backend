package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepo extends JpaRepository<Hospital, String> {
    List<Hospital> findBySsid(String ssid);
}
