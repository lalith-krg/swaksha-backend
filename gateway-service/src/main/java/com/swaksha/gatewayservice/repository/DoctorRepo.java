package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.Doctor;
import com.swaksha.gatewayservice.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor, String> {
}
