package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.HiuUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HiuUrlRepo extends JpaRepository<HiuUrl, String> {
    List<HiuUrl> findByHiuSsid(String hiuSsid);
}
