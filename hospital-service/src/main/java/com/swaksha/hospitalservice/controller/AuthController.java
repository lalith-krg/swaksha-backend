package com.swaksha.hospitalservice.controller;


import com.swaksha.hospitalservice.dto.AuthRequest;
import com.swaksha.hospitalservice.dto.AuthResponse;
import com.swaksha.hospitalservice.dto.RegisterRequest;
import com.swaksha.hospitalservice.dto.SendOtp;
import com.swaksha.hospitalservice.service.AuthService;
import com.swaksha.hospitalservice.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins= {"*"})
public class AuthController {
    private final AuthService service;
    private final OtpService otp_service;



/*    public AuthController(AuthService service,AuthenticationService authenticationService)
    {
        this.service=service;
        this.otp_service = authenticationService;
    }*/
    @CrossOrigin
    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody SendOtp request){
        System.out.println(request.getPhone_number());
        return otp_service.send_otp(request.getPhone_number());
    }
    @CrossOrigin
    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){

        System.out.println(request.getPhone_number());
        System.out.println(request.getOtp());

        String resp=otp_service.verify_otp(request.getOtp(),request.getPhone_number());
        System.out.println(resp);

        if(resp.equals("approved"))  return ResponseEntity.ok(service.register(request));
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate( @RequestBody AuthRequest request){

        System.out.println(request.getSsid());
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/demo")
    public String sayHello(Authentication authentication){

        return authentication.getName();
    }



}
