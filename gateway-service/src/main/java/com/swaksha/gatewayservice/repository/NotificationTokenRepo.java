package com.swaksha.gatewayservice.repository;

import com.swaksha.gatewayservice.entity.NotificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationTokenRepo extends CrudRepository<NotificationToken, String> {
    NotificationToken findBySsid(String ssid);
}