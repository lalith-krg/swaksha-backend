package com.swaksha.gatewayservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.swaksha.gatewayservice.entity.ApiKeys;
import java.util.List;

@Repository
public interface APIRepo extends JpaRepository<ApiKeys, Long> {
    List<ApiKeys> findBySsid(String ssid);
}
