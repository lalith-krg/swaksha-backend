package com.swaksha.patientservice.service;

import com.swaksha.patientservice.entity.Patient;
import com.swaksha.patientservice.entity.PatientCred;
import com.swaksha.patientservice.repository.PatientCredRepo;
import com.swaksha.patientservice.repository.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor
public class PatientService {

    @Autowired
    private final PatientRepo patientRepo;

    @Autowired
    private PatientCredRepo patientCredRepo;

//    public Patient registerMobile(Patient newPatient, PatientCred newPatientCred) {
    public Patient registerMobile(Patient newPatient) {
        patientRepo.save(newPatient);
//        patientCredRepo.save(newPatientCred);
        Patient patient = newPatient;
        return patient;
    }

    public Patient registerGovId(Patient newPatient) {
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

    public boolean dashboard(Patient patient) {
        return true;
    }

    public boolean viewProfile(Patient patient) {
        return true;
    }

    public boolean updateProfile(Patient patient) {
        return true;
    }

    public boolean deleteProfile(Patient patient) {
        return true;
    }

    public boolean resetPassword(Patient patient) {
        return true;
    }

    public String getSSID(){
        return "p1";
    }

}
