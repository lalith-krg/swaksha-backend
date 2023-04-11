package com.swaksha.hospitalservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
    private String ssid;

    private String password;

    private String first_name;


    private String last_name;

    private String otp;
    private String phone_number;
    private String gender;

    private LocalDateTime dob;


    private String email;

    private String address;
    private String city;
    private String state;





}
