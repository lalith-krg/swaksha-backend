package com.swaksha.gatewayservice.hospital;


import com.swaksha.gatewayservice.patient.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway/hospital")
public class HospitalController {

    // Profile controller

    @GetMapping("/demo")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured hospital end point");
    }
}
