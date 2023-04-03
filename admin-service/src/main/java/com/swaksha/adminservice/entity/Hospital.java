package com.swaksha.adminservice.entity;

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
    private String ssid;

    @Column(nullable = false)
    private String hospital_name;

    @Column
    private String phone_num_1;

    @Column
    private String phone_num_2;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;
}
