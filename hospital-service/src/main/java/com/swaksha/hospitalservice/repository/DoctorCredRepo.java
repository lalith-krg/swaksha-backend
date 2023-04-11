package com.swaksha.hospitalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.swaksha.hospitalservice.entity.DoctorCred;

import java.util.Optional;

public interface DoctorCredRepo extends JpaRepository<DoctorCred, String> {
    Optional<DoctorCred> findBySsid(String Ssid);
}
