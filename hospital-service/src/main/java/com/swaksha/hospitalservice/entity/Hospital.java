package com.swaksha.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private String ssID;

    @Column(nullable = false)
    private String hospitalName;

    @Column(nullable = false)
    private String phoneNum1;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;
}
