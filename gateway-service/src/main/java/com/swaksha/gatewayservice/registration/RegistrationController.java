package com.swaksha.gatewayservice.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gateway/registration")
public class RegistrationController {

    private RegistrationService registrationService = new RegistrationService();

    record PatientRegCM(String SSID, String phoneNum){}

    @PostMapping("/mobile/gen_ssid")
    public void generateSSID(String mobileNum){
        //
    }

    @PostMapping("/mobile/send_otp")
    public void sendOtp(String mobileNum){
        //
    }

    @PostMapping("/mobile/verify_otp")
    public void verifyOtp(String mobileNum){
        //
    }


    // Auto register with cm
    @PostMapping("/registerCM")
    public void registerCM(@RequestBody PatientRegCM patientRegCM){
        //
    }
}
