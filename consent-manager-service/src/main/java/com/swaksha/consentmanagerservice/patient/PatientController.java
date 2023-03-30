package com.swaksha.consentmanagerservice.patient;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cm/patient/auth")
public class PatientController {

    @Autowired
    private final PatientService patientService;

    record Account(String ssid, String phoneNum, String encPin){}

    record PinToVerifyBody(String SSID, String encPin){}

    record UpdateDetails(String SSID, String phoneNum, String OTP, String newPin){}

    // Create patient credentials
    @PostMapping("/register")
    public boolean registerCM(@RequestBody Account account){
        // Save credentials
        boolean registered = this.patientService.register(account.ssid, account.phoneNum, account.encPin);
        return registered;
    }

    // Reset PIN
    @PostMapping("/resetPin")
    public void resetPin(String SSID, String phoneNum){
        // Send OTP to registered phoneNum
    }

    // Confirm new PIN
    @PostMapping("/confirmNewPin")
    public void confirmNewPin(@RequestBody UpdateDetails updateDetails){
        // Verify OTP

        // Save new PIN
        boolean updated = this.patientService.updatePin(updateDetails.SSID, updateDetails.newPin);
    }

    // Verify PIN
    @PostMapping("/verifyPin")
    public boolean verifyPin(@RequestBody PinToVerifyBody pinToVerifyBody){
        // check pin
        boolean validity = this.patientService.verifyPin(pinToVerifyBody.SSID, pinToVerifyBody.encPin);
        return validity;
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
