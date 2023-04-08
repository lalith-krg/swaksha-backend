package com.swaksha.hospitalservice.controller;

import com.swaksha.hospitalservice.entity.Patient;
import com.swaksha.hospitalservice.entity.Hospital;
import com.swaksha.hospitalservice.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/health/facility")
public class HospitalController {

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    record doctorRequestBodyWithoutConsent(String docSSID, String patientSSID){}

//    record doctorRequestBodyWithConsent(String docSSID, String hiuSSID, String patientSSID, ConsentObj consentObj){}


    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/registerPatient")
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient){
        boolean response = hospitalService.registerPatient(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    Data Transfer APIs

    //    Consent Manager Calls HIP for EHR
    @PostMapping("/ehr/hip/request")
    public ResponseEntity<Patient> requestEHRFromHIP(@RequestBody Patient patient){
        boolean response = hospitalService.requestEHRFromHIP(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    HIP transfers EHR to HIU
    @PostMapping("/ehr/transfer")
    public ResponseEntity<Patient> transferEHRFromHIP(@RequestBody Patient patient){
        boolean response = hospitalService.transferEHRFromHIP(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    HIU receives transfer from HIP
    @PostMapping("/ehr/hiu/on-request")
    public ResponseEntity<Patient> receiveTransfer(@RequestBody Patient patient){
        boolean response = hospitalService.receiveTransfer(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    CM notifies HIP about consent status
    @PostMapping("/consent/hip/notify")
    public ResponseEntity<Patient> consentNotifyHIP(@RequestBody Patient patient){
        boolean response = hospitalService.consentNotifyHIP(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    CM notifies HIU about consent status
    @PostMapping("/consent/hiu/notify")
    public ResponseEntity<Patient> consentNotifyHIU(@RequestBody Patient patient){
        boolean response = hospitalService.consentNotifyHIU(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    HIU requests CM for consent
    @PostMapping("/consent/hiu/request")
    public ResponseEntity<Patient> consentRequestFromHIU(@RequestBody Patient patient){
        boolean response = hospitalService.consentRequestFromHIU(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    HIU confirms consent from CM
    @PostMapping("/consent/hiu/confirmRequest")
    public ResponseEntity<Patient> confirmConsentRequestFromHIU(@RequestBody Patient patient){
        boolean response = hospitalService.confirmConsentRequestFromHIU(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/hospital/request")
    public void hiuRequest(@RequestBody doctorRequestBodyWithoutConsent doctorRequestBodyWithoutConsent){

        // call /gateway/request/hiu/request
        String url = "http://localhost:8999/gateway/request/hiu/request";

        HttpEntity<doctorRequestBodyWithoutConsent> consentEntity = new HttpEntity<>(doctorRequestBodyWithoutConsent);

        this.restTemplate.postForEntity(url, consentEntity, Boolean.class);
    }
}
