package com.swaksha.patientservice.controller;

import com.swaksha.patientservice.entity.Patient;
import com.swaksha.patientservice.entity.PatientCred;
import com.swaksha.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping(path="/register/mobile")
//    public ResponseEntity<Patient> registerMobile(@RequestBody Patient newPatient, @RequestBody PatientCred patientCred){
    public ResponseEntity<Patient> registerMobile(@RequestBody Patient newPatient){
        Patient patient = patientService.registerMobile(newPatient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register( HttpEntity<RegisterRequest> request){
        String url="http://localhost:9005/api/v1/auth/register";
        RestTemplate restTemplate=new RestTemplate();

        ResponseEntity< AuthResponse>response =restTemplate.exchange(url, HttpMethod.POST,request,AuthResponse.class);
        return response;

    }

    @PostMapping(path="/register/govId")
//    public ResponseEntity<Patient> registerGovId(@RequestBody Patient newPatient, @RequestBody PatientCred patientCred){
    public ResponseEntity<Patient> registerGovId(@RequestBody Patient newPatient){
        Patient patient = patientService.registerGovId(newPatient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }


    public ResponseEntity<PatientCred> login(@RequestBody PatientCred patientCred) {

        boolean response = patientService.login(patientCred);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(patientCred, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(HttpEntity<AuthRequest> request) {
        //    var user=repository.findBySsid(request.getSsid());

        //  if(user.isEmpty())user=repository.findByAbhaId(request.getAbhaId()).orElseThrow();
        String url="http://localhost:9005/api/gateway/hospital/demo";
        RestTemplate restTemplate=new RestTemplate();

        ResponseEntity< AuthResponse>response =restTemplate.exchange(url, HttpMethod.POST,request,AuthResponse.class);
        return response;

    }
    @GetMapping("/demo")
    public ResponseEntity<String> sayHello(HttpEntity<String> request){

        String url="http://localhost:9005/gateway/patient/demo";
        RestTemplate restTemplate=new RestTemplate();

        ResponseEntity< String>response =restTemplate.exchange(url, HttpMethod.GET,request,String.class);
        return response;
    }

    @GetMapping(path ="/dashboard")
    public ResponseEntity<Patient> dashboard(@RequestBody Patient patient){

        boolean response = patientService.dashboard(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path ="/viewProfile")
    public ResponseEntity<Patient> viewProfile(@RequestBody Patient patient){

        boolean response = patientService.viewProfile(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path ="/updateProfile")
    public ResponseEntity<Patient> updateProfile(@RequestBody Patient patient){

        boolean response = patientService.updateProfile(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path ="/deleteProfile")
    public ResponseEntity<Patient> deleteProfile(@RequestBody Patient patient){

        boolean response = patientService.deleteProfile(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path ="/resetPassword")
    public ResponseEntity<Patient> resetPassword(@RequestBody Patient patient){

        boolean response = patientService.resetPassword(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
