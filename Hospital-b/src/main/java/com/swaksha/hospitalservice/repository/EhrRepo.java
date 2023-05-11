package com.swaksha.hospitalservice.repository;

import com.swaksha.hospitalservice.entity.Ehr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EhrRepo extends JpaRepository<Ehr, String> {
 //  @Query("select r from Ehr r where r.patientSsID=ssid and r.creationDate <= startDate and r.creationDate>=endDate")
    List<Ehr> findByPatientSsidAndCreationDateBetween( String ssid,LocalDate startDate, LocalDate endDate);
    List<Ehr> findByPatientSsid(String ssid);

}
