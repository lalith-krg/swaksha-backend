package com.swaksha.hospitalservice.repository;

import com.swaksha.hospitalservice.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsentRepo extends JpaRepository<Consent, String> {
    List<Consent> findByConsentId(String consentId);
}
