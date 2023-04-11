package com.swaksha.hospitalservice.repository;

import com.swaksha.hospitalservice.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor,String> {
}
