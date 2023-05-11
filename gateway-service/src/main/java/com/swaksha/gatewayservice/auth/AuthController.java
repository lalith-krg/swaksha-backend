package com.swaksha.gatewayservice.auth;

import com.swaksha.gatewayservice.authentication.AuthenticationService;

import com.swaksha.gatewayservice.repository.APIRepo;

import io.netty.handler.codec.http.HttpRequest;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.net.http.HttpResponse;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins= {"*"})
public class AuthController {

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();
    private final AuthService service;
    private final AuthenticationService otp_service;


    record assignRequest(String ssid){}
    record notificationToken(String token){}
    record notificationTokenSsid(String ssid){}

    record PatientAuthCM (String ssid, String phoneNum, String encPin) {}

    record PatientAuthCreationResponse (String resp){};


    @CrossOrigin
    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody SendOtp request){
        System.out.println(request.getPhone_number());
        return otp_service.send_otp(request.getPhone_number());
    }
    @CrossOrigin
    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> register( @RequestBody RegisterRequest request){

        System.out.println(request.getPhone_number());
        System.out.println(request.getOtp());

        String resp=otp_service.verify_otp(request.getOtp(),request.getPhone_number());
        System.out.println(resp);

        System.out.println(request.getSsid());
        System.out.println(request.getPhone_number());
        System.out.println(request.getCmPinPassword());

//        if(resp.equals("approved"))  return ResponseEntity.ok(service.register(request));



        if(resp.equals("approved")) {

            AuthResponse authresponse = service.register(request);

            String path = "http://localhost:9006/cm/patient/auth/register";

            PatientAuthCM patientAuthCM = new PatientAuthCM(
                    authresponse.getSsid(),
                    request.getPhone_number(),
                    request.getCmPinPassword()
            );

//            System.out.println

            HttpEntity<PatientAuthCM> patientAuthEntity = new HttpEntity<>(patientAuthCM);
            ResponseEntity<PatientAuthCreationResponse> res = this.restTemplate.postForEntity(path, patientAuthEntity,
                    PatientAuthCreationResponse.class);

            System.out.println("lol");
            System.out.println(res);

            return ResponseEntity.ok(authresponse);
        }


        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate( @RequestBody AuthRequest request){

        System.out.println(request.getSsid());
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/assign-api-key")
    public String createApiKey(@RequestBody assignRequest request){
        String api_key = service.assignAPIKey(request.ssid);
        return api_key;
    }

//    @PostMapping("/verify-api-key")
//    public boolean verifyApiKey(@RequestBody assignRequest request, HttpServletRequest http_request){
//        String api_key = http_request.getHeader("swaksha-api-key");
//        boolean valid = service.verifyAPIKey(request.ssid, api_key);
//        return valid;
//    }

    @PostMapping("/assign-notification-token")
    public boolean assignNotificationTokenController(@RequestBody notificationToken notification_token, Authentication authentication){
        service.assignNotificationToken(authentication.getName(), notification_token.token);
        return true;
    }

    @GetMapping("/get-notification-token")
    public String assignNotificationTokenController(@RequestBody notificationTokenSsid ntssid){
        String token = service.getNotificationToken(ntssid.ssid);
        return token;
    }

    @PostMapping("/demo")
    public String sayHello(){
        return "hello from patient endpoint";
    }

}
