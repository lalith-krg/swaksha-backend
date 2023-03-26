package com.swaksha.consentmanagerservice.repository;

import com.swaksha.consentmanagerservice.entity.PatientAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientAuthRepo extends JpaRepository<PatientAuth, String> {
}
