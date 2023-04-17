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

    // Create patient credentials
    @PostMapping("/register")
    public boolean registerPatient(@RequestBody Account account){
        // Save credentials
        boolean registered = this.patientService.registerPatient(account.ssid, account.phoneNum, account.encPin);
        return registered;
    }

    // Verify PIN
    @PostMapping("/verifyPin")
    public boolean verifyPin(@RequestBody PinToVerifyBody pinToVerifyBody){
        // check pin
        boolean validity = this.patientService.verifyPin(pinToVerifyBody.SSID, pinToVerifyBody.encPin);
        return validity;
    }

    // Confirm new PIN
    @PostMapping("/updateAccount")
    public void updateAccount(@RequestBody Account account){
        // Save new PIN
        boolean updated = this.patientService.updateAccount(account.ssid(), account.phoneNum(),
                account.encPin());
    }

}
