package com.swaksha.consentmanagerservice.patient;

import com.swaksha.consentmanagerservice.entity.PatientAuth;
import com.swaksha.consentmanagerservice.repository.PatientAuthRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Component
@RequiredArgsConstructor
public class PatientService {

    @Autowired
    private final PatientAuthRepo patientAuthRepo;

//    @Autowired
    public boolean registerPatient(String ssid, String phoneNum, String encPin) {
        PatientAuth patientAuth = this.patientAuthRepo.save(new PatientAuth(ssid, phoneNum, encPin));
//        this.patientAuthRepo.save(new PatientAuth(ssid, phoneNum, encPin));
        if (patientAuth == null){
            return false;
        }
        return true;
    }

//    @Autowired
    public boolean verifyPin(String patientSSID, String encPin) {
//        String originalPin = this.patientAuthRepo.getPatientPin(patientSSID);
        ArrayList<PatientAuth> p = (ArrayList<PatientAuth>) this.patientAuthRepo.findBySsid(patientSSID);

//        System.out.println(p.get(0).getAuthPin());

        if (p.size()<1){
            return false;
        }

        else if (Objects.equals(p.get(0).getAuthPin(), encPin)){
            return true;
        }

        return false;
    }

    public boolean updateAccount(String ssid, String phoneNum, String encPin) {
        PatientAuth patientAuth = this.patientAuthRepo.save(new PatientAuth(ssid, phoneNum, encPin));
//        this.patientAuthRepo.save(new PatientAuth(ssid, phoneNum, encPin));
        if (patientAuth == null){
            return false;
        }
        return true;
    }
}
