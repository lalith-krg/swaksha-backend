package com.swaksha.gatewayservice.auth;

import com.swaksha.gatewayservice.authentication.AuthenticationService;
import com.swaksha.gatewayservice.repository.APIRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService service;
    private final AuthenticationService otp_service;

    record assignRequest(String ssid){}

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
    public ResponseEntity<AuthResponse> register( @RequestBody RegisterRequest request){

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

    // @GetMapping("/create-api-key")
    // public boolean createApiKey(HttpServletRequest request){
    //     String key = request.getHeader("swaksha-api-key");
    //     System.out.println(key);
    //     String api_key = service.createAPIKey();
    //     String api_key_hashed = AuthService.generateSHA256(api_key);
    //     boolean is_same = AuthService.verifySHA256(api_key, api_key_hashed);
    //     System.out.println(api_key);
    //     System.out.println(api_key_hashed);
    //     return is_same;
    // }

    @PostMapping("/assign-api-key")
    public String createApiKey(@RequestBody assignRequest request){
        String api_key = service.assignAPIKey(request.ssid);
        return api_key;
    }

    @PostMapping("/verify-api-key")
    public boolean verifyApiKey(@RequestBody assignRequest request, HttpServletRequest http_request){
        String api_key = http_request.getHeader("swaksha-api-key");
        boolean valid = service.verifyAPIKey(request.ssid, api_key);
        return valid;
    }

    @PostMapping("/demo")
    public String sayHello(){
        return "hello from patient endpoint";
    }
}
