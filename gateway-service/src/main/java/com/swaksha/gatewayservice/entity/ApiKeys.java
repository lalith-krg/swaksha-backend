package com.swaksha.gatewayservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "api_keys")
public class ApiKeys {

    @Id
    @Column(unique = true, nullable = false)
    String ssid;

    @Column(unique = true, nullable = false)
    String apiKey;
}
