package com.swaksha.hospitalservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @Column(unique = true, nullable = false)
    private String ssID;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false)
    private String phoneNum;

    @Column
    private LocalDate dob;

    private String gender;

    private String email;

    private String city;

    private String state;
}
