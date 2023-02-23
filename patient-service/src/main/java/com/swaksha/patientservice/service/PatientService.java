package com.swaksha.patientservice.service;

import com.swaksha.patientservice.entity.Patient;
import com.swaksha.patientservice.entity.PatientCred;
import com.swaksha.patientservice.repository.PatientCredRepo;
import com.swaksha.patientservice.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private PatientCredRepo patientCredRepo;

//    public Patient register(Patient newPatient, PatientCred newPatientCred) {
    public Patient register(Patient newPatient) {
        patientRepo.save(newPatient);
//        patientCredRepo.save(newPatientCred);
        Patient patient = newPatient;
        return patient;
    }

    public boolean login(PatientCred patientLoginCred){
        try {
            // Login with username
            PatientCred patientCred = patientCredRepo.findByUsername(patientLoginCred.getUsername());

            if (patientCred.getPassword().matches(patientCred.getPassword())) {
                return true;
            }

//            // Login with ssid
//            patientCred = patientCredRepo.findBySSID(patientLoginCred.getSsID());
//
//            if (patientCred.getPassword().matches(patientCred.getPassword())) {
//                return true;
//            }
//
//            // Login with phone
//            patientCred = patientCredRepo.findByPhoneNum(patientLoginCred.getPhoneNum());
//
//            if (patientCred.getPassword().matches(patientCred.getPassword())) {
//                return true;
//            }

            return false;
        }
        catch(Exception e){
            //
        }

        return false;
    }
}
