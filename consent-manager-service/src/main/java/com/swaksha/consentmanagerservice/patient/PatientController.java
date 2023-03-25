package com.swaksha.consentmanagerservice.patient;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/cm/patient/auth")
public class PatientController {

    // Create patient credentials
    @PostMapping("/register")
    public void registerCM(JSONObject newAcc){
        // Save credentials
    }

    // Reset PIN
    @PostMapping("/resetPin")
    public void resetPin(String SSID, String phoneNum){
        // Send OTP to registered phoneNum
    }

    // Confirm new PIN
    @PostMapping("/confirmNewPin")
    public void confirmNewPin(String SSID, String phoneNum, String OTP){
        // Verify OTP and save new PIN
    }

    // Verify PIN
    @PostMapping("/verifyPin")
    public void verifyPin(String SSID){
        // check pin
    }

    /*
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
