package com.swaksha.gatewayservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Hospital_Credentials")

public class HospitalCred {

    @Id
    @Column(unique = true, nullable = false)
    private String ssid;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "ssid", referencedColumnName = "ssid")
    private Hospital hospital;
}
