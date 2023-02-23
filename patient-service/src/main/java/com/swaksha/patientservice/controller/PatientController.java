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

    @PostMapping(path="/registerPatient")
//    public ResponseEntity<Patient> register(@RequestBody Patient newPatient, @RequestBody PatientCred patientCred){
    public ResponseEntity<Patient> register(@RequestBody Patient newPatient){
        Patient patient = patientService.register(newPatient);
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
}
