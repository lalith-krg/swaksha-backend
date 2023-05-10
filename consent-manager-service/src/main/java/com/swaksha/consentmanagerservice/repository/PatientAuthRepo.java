package com.swaksha.consentmanagerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swaksha.consentmanagerservice.entity.PatientAuth;

import java.util.List;

@Repository
public interface PatientAuthRepo extends JpaRepository<PatientAuth, String> {
    List<PatientAuth> findBySsid(String ssid);
}