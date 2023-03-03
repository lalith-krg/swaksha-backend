package com.swaksha.gatewayservice.registration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway/registration")
public class RegistrationController {

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
}
