package com.swaksha.gatewayservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Hospital_Details")

public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private String ssid;

    @Column(nullable = false)
    private String hospital_name;

    @Column(nullable = false)
    private String phone_num1;

    @Column(nullable = false)
    private String phone_num2;

    private String email;

    private String address;
    private String city;
    private String state;

}
