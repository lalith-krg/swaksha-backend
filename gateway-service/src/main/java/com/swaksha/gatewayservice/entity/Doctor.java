package com.swaksha.gatewayservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Doctor_Details")

public class Doctor {

    @Id
    @Column(unique = true)
    private String ssid;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String phone_number;


    private LocalDateTime dob;

    @Column(nullable = false)
    private String gender;

    private String email;

    private String address;
    private String city;
    private String state;

}
