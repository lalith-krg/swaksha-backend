package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.Doctor;
import com.swaksha.gatewayservice.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, String> {
    List<Doctor> findBySsid(String ssid);
}
