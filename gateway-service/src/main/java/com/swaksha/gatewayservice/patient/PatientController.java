package com.swaksha.gatewayservice.patient;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway/patient")
public class PatientController {

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

    // Patient can revoke consents
    @PostMapping("/revokeConsent")
    public void revokeConsent(String SSID, JSONObject consentObj){
        // Check SSID

        // Place revoke consent object to consent manager
    }

    /*
    // Consent Management
    @PostMapping("/getConsent")
    public void getConsent(String docSSID, String hiuSSID, String patientSSID){
        // Check legitimacy

        // Post a form to patient
    }

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
    */
}
