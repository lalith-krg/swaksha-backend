package com.swaksha.patientservice.controller;

import com.swaksha.patientservice.entity.Patient;
import com.swaksha.patientservice.entity.PatientCred;
import com.swaksha.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path="/register/govId")
//    public ResponseEntity<Patient> registerGovId(@RequestBody Patient newPatient, @RequestBody PatientCred patientCred){
    public ResponseEntity<Patient> registerGovId(@RequestBody Patient newPatient){
        Patient patient = patientService.registerGovId(newPatient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping(path ="/login")
    public ResponseEntity<PatientCred> login(@RequestBody PatientCred patientCred) {

        boolean response = patientService.login(patientCred);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(patientCred, HttpStatus.OK);
    }

    @GetMapping(path ="/dashboard")
    public ResponseEntity<Patient> dashboard(Patient patient){

        boolean response = patientService.dashboard(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path ="/viewProfile")
    public ResponseEntity<Patient> viewProfile(Patient patient){

        boolean response = patientService.viewProfile(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path ="/updateProfile")
    public ResponseEntity<Patient> updateProfile(Patient patient){

        boolean response = patientService.updateProfile(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path ="/deleteProfile")
    public ResponseEntity<Patient> deleteProfile(Patient patient){

        boolean response = patientService.deleteProfile(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path ="/resetPassword")
    public ResponseEntity<Patient> resetPassword(Patient patient){

        boolean response = patientService.resetPassword(patient);

        if(!response) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
