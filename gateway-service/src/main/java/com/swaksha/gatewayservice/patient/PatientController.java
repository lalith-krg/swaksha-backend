package com.swaksha.gatewayservice.patient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway/patient")
public class PatientController {


    @PostMapping("/init")
    public void initConsent(String ssid){
        //
    }

    // Placing request for data
    @PostMapping("/request")
    public void requestData(String ssid){
        //
    }

    // Confirm request with otp
    @PostMapping("/confirm")
    public void confirmWithOtp(String ssid){
        //
    }

    // HIP acknowledging request
    @PostMapping("/on-notify")
    public void notifyPatient(String ssid){
        //
    }

    // Profile controller

    @PostMapping("/getSSID")
    public void getSSID(String mobileNum){
        //
    }

    @PostMapping("/viewProfile")
    public void viewProfile(String mobileNum){
        //
    }

    @PostMapping("/updateProfile")
    public void updateProfile(String mobileNum){
        //
    }

}
