package com.swaksha.consentmanagerservice.patient;

import com.swaksha.consentmanagerservice.entity.PatientAuth;
import com.swaksha.consentmanagerservice.repository.PatientAuthRepo;

import java.util.ArrayList;

public class PatientService {

    private PatientAuthRepo patientAuthRepo;

    public boolean verifyPin(String patientSSID, String encPin) {
//        String originalPin = this.patientAuthRepo.getPatientPin(patientSSID);
        ArrayList<PatientAuth> p = (ArrayList<PatientAuth>) this.patientAuthRepo.findBySsid(patientSSID);

        if (p.size()<1){
            return false;
        }

        else if (p.get(0).getAuthPin() == encPin){
            return true;
        }

        return false;
    }

    public boolean register(String ssid, String phoneNum, String encPin) {
        PatientAuth patientAuth = this.patientAuthRepo.save(new PatientAuth(ssid, phoneNum, encPin));
        if (patientAuth == null){
            return false;
        }
        return true;
    }

    public boolean updatePin(String ssid, String newPin) {
        int update = this.patientAuthRepo.updatePin(ssid, newPin);
        return true;
    }
}
