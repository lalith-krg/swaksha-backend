package com.swaksha.hospitalservice.requests;

import com.swaksha.hospitalservice.entity.Consent;
import com.swaksha.hospitalservice.entity.Ehr;
import com.swaksha.hospitalservice.entity.Patient;
import com.swaksha.hospitalservice.repository.ConsentRepo;
import com.swaksha.hospitalservice.repository.EhrRepo;
import com.swaksha.hospitalservice.repository.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class RequestService {
    private final EhrRepo ehrRepo;
    private final ConsentRepo consentRepo;
    private final PatientRepo patientRepo;
    
    public RequestController.ConsentObj findConsentWithID(String consentID){
        Consent consent = ((ArrayList<Consent>) this.consentRepo.findByConsentID(consentID)).get(0);
        return cObjOf(consent);
    }

    public boolean updateConsentObj(RequestController.ConsentObj consentObj) {
        return save(consentOf(consentObj));
    }

    private boolean save(Consent consentObj){
        Consent consent = this.consentRepo.save(consentObj);

        if(consent == null){
            return false;
        }
        return true;
    }

    public ArrayList<Ehr> findPatientEhrData(String patientSSID, LocalDate parse, LocalDate parse1) {
        return (ArrayList<Ehr>) this.ehrRepo.findByPatientSsidAndCreationDateBetween(patientSSID, parse, parse1);
    }

    public ArrayList<Ehr> fetchEhrData(String ssid){
        ArrayList<Ehr> ehrData = (ArrayList<Ehr>) ehrRepo.findByPatientSsid(ssid);
        return ehrData;
    }

    public Patient findPatientById(String patientSSID) {
        return this.patientRepo.findPatientBySsid(patientSSID);
    }

    public void save(Ehr ehr) {
        this.ehrRepo.save(ehr);
    }

    public boolean deleteConsentObj(String consentId) {
        ArrayList<Consent> consents = (ArrayList<Consent>) this.consentRepo.findByConsentID(consentId);
        if(consents.size()<1)
            return false;

        this.consentRepo.delete(consents.get(0));

        return true;
    }

    private RequestController.ConsentObj cObjOf(Consent consent){
        return new RequestController.ConsentObj(consent.getDoctorSSID(),
                consent.getHiuSSID(), consent.getPatientSSID(), consent.getHipSSID(),
                consent.getDataAccessStartDate(), consent.getDataAccessEndDate(),
                consent.getRequestInitiatedDate(), consent.getConsentApprovedDate(), consent.getConsentEndDate(),
                consent.getConsentID(), consent.isSelfConsent(), consent.isApproved());
    }

    private Consent consentOf(RequestController.ConsentObj consentObj){
        return new Consent(consentObj.consentID(), consentObj.consentEndDate(), consentObj.isApproved(),
                consentObj.selfConsent(),
                consentObj.doctorSSID(), consentObj.hiuSSID(), consentObj.patientSSID(), consentObj.hipSSID(),
                consentObj.dataAccessStartDate(), consentObj.dataAccessEndDate(), consentObj.requestInitiatedDate(),
                consentObj.consentApprovedDate());
    }
}
