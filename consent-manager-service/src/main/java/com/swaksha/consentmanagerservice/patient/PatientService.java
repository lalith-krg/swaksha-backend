package com.swaksha.consentmanagerservice.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.swaksha.consentmanagerservice.entity.PatientAuth;
import com.swaksha.consentmanagerservice.repository.PatientAuthRepo;

import java.util.ArrayList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Component
@RequiredArgsConstructor
public class PatientService {

    @Autowired
    private final PatientAuthRepo patientAuthRepo;

    public boolean registerPatient(String ssid, String phoneNum, String encPin) {
        String sha256_encPin = PatientService.generateSHA256(encPin);
        PatientAuth patientAuth = this.patientAuthRepo.save(new PatientAuth(ssid, phoneNum, sha256_encPin));

        if(patientAuth == null){
            return false;
        }
        return true;
    }

    public boolean verifyPin(String patientSSID, String encPin) {
        ArrayList<PatientAuth> p = (ArrayList<PatientAuth>) this.patientAuthRepo.findBySsid(patientSSID);

        Boolean ok = false;
        for(int i=0;i<p.size();i++){
            ok |= PatientService.verifySHA256(encPin, p.get(i).getAuthPin());
        }

        return ok;
    }

    public boolean updateAccount(String ssid, String phoneNum, String encPin) {
        String sha256_encPin = PatientService.generateSHA256(encPin);
        PatientAuth patientAuth = this.patientAuthRepo.save(new PatientAuth(ssid, phoneNum, sha256_encPin));

        if(patientAuth == null){
            return false;
        }
        return true;
    }

    public static String generateSHA256(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifySHA256(String message, String hash) {
        return generateSHA256(message).equals(hash);
    }
}