package com.swaksha.hospitalservice.repository;

import com.swaksha.hospitalservice.entity.Ehr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EhrRepo extends JpaRepository<Ehr, String> {
}
