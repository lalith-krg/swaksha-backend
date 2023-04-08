package com.swaksha.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Patient {

    @Id
    @Column(unique = true, nullable = false)
    private String ssID;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private int age;

    private String email;

    private String address;

}
