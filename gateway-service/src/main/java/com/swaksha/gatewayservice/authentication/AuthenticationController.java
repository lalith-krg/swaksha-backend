package com.swaksha.gatewayservice.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/gateway")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    public AuthenticationController(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    record send_otp_body(String mobile_number){}
    @CrossOrigin
    @PostMapping("/send-otp")
    public String send_otp(@RequestBody send_otp_body send_otp_rec)
    {
        return authenticationService.send_otp(send_otp_rec.mobile_number);
    }

    record verify_otp_body(String mobile_number,String otp){}
    @CrossOrigin
    @PostMapping("/verify-otp")
    public String verify_otp(@RequestBody verify_otp_body verify_otp_rec)
    {
        return authenticationService.verify_otp(verify_otp_rec.otp,verify_otp_rec.mobile_number);
    }
}


// @RestController
// @RequestMapping("api/v1")
// public class DefaultController {
//     @GetMapping("/index")
//     public String index(){
//         return "Hello World";
//     }
// }
