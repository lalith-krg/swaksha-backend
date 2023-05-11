package com.swaksha.hospitalservice.repository;

import com.swaksha.hospitalservice.entity.Ehr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EhrRepo extends JpaRepository<Ehr, String> {
    List<Ehr> findByPatientSsid(String ssid);
    List<Ehr> findByPatientSsidAndCreationDateBetween(String ssid, LocalDate startDate, LocalDate endDate);
}
