package com.swaksha.gatewayservice.profile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway/account")
public class ProfileController {

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
