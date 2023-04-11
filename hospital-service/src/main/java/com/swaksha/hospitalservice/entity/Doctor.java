package com.swaksha.hospitalservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Doctor_Details")
public class Doctor {
    @Id
    @Column(unique = true, nullable = false)
    private String ssid;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false)
    private String phone_number;

    private String address;

    @Column(nullable = true)
    private LocalDate dob;

    private String gender;

    private String email;

    private String city;

    private String state;
}
