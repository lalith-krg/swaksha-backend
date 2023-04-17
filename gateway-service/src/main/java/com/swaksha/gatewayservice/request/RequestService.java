package com.swaksha.gatewayservice.request;

import com.swaksha.gatewayservice.entity.Doctor;
import com.swaksha.gatewayservice.entity.HiuUrl;
import com.swaksha.gatewayservice.entity.Hospital;
import com.swaksha.gatewayservice.entity.Patient;
import com.swaksha.gatewayservice.repository.DoctorRepo;
import com.swaksha.gatewayservice.repository.HiuUrlRepo;

import com.swaksha.gatewayservice.repository.HospitalRepo;
import com.swaksha.gatewayservice.repository.PatientRepo;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Component
@RequiredArgsConstructor
public class RequestService {

    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final HospitalRepo hospitalRepo;
    private final HiuUrlRepo hiuUrlRepo;


    public boolean validateSSID(String SSID) {
        boolean validity = false;
        System.out.println(SSID);
        ArrayList<Patient> p_arr = (ArrayList<Patient>) this.patientRepo.findBySsid(SSID);
        //System.out.println(p_arr.get(0).getSsid());

        System.out.println(p_arr.size());
        if (p_arr.size()>0 && p_arr.get(0).getSsid().equals(SSID)){
            return true;
        }

        ArrayList<Doctor> d_arr = (ArrayList<Doctor>) this.doctorRepo.findBySsid(SSID);
        if (d_arr.size()>0 && d_arr.get(0).getSsid().equals(SSID)){
            return true;
        }

        ArrayList<Hospital> h_arr = (ArrayList<Hospital>) this.hospitalRepo.findBySsid(SSID);
        if (h_arr.size()>0 && h_arr.get(0).getSsid().equals(SSID)){
            return true;
        }

        return validity;
    }

    public boolean checkToApproveConsentFields(RequestController.ConsentObj consentObj) {
        boolean validity = true;

        if(consentObj.selfConsent() != true){
            validity = validateSSID(consentObj.doctorSSID()) && validateSSID(consentObj.hiuSSID());
        }
        validity = validity && validateSSID(consentObj.patientSSID()) && validateSSID(consentObj.hipSSID());
        if(!validity){
            return false;
        }

        if(consentObj.consentEndDate() == null || consentObj.requestInitiatedDate() == null)
            return false;

        if(consentObj.requestInitiatedDate().isAfter(consentObj.consentEndDate()))
            validity = false;

        return validity;
    }

    public boolean saveHiuLink(String hiuSSID, String dataPostUrl) {
        HiuUrl hiuUrl = this.hiuUrlRepo.save(new HiuUrl(hiuSSID, dataPostUrl));

        if(hiuUrl == null){
            return false;
        }

        return true;
    }

    public String getHiuLink(String hiuSSID) {
        ArrayList<HiuUrl> urls = (ArrayList<HiuUrl>) this.hiuUrlRepo.findByHiuSsid(hiuSSID);

        if(urls.size()<1){
            return "invalid url";
        }

        return urls.get(0).getHiuUrl();
    }
}
