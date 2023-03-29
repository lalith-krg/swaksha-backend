package com.swaksha.consentmanagerservice.patient;

import com.swaksha.consentmanagerservice.entity.PatientAuth;
import com.swaksha.consentmanagerservice.repository.PatientAuthRepo;

public class PatientService {

    private PatientAuthRepo patientAuthRepo;

    public boolean verifyPin(String patientSSID, String encPin) {
//        String originalPin = this.patientAuthRepo.getPatientPin(patientSSID);
        return true;
    }

    public boolean register(String ssid, String phoneNum, String encPin) {
        PatientAuth patientAuth = this.patientAuthRepo.save(new PatientAuth(ssid, phoneNum, encPin));
        if (patientAuth == null){
            return false;
        }
        return true;
    }

    public boolean updatePin(String ssid, String newPin) {
//        int update = this.patientAuthRepo.updatePin(ssid, newPin);
        return true;
    }
}
