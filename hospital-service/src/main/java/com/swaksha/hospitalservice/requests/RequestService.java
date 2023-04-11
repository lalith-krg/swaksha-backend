package com.swaksha.hospitalservice.requests;

import com.swaksha.hospitalservice.entity.Consent;
import com.swaksha.hospitalservice.repository.ConsentRepo;
import com.swaksha.hospitalservice.repository.EhrRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Component
@RequiredArgsConstructor
public class RequestService {
    private final EhrRepo ehrRepo;
    private final ConsentRepo consentRepo;
    
    public RequestController.ConsentObj findConsentWithID(String consentID){
        Consent consent = ((ArrayList<Consent>) this.consentRepo.findByConsentID(consentID)).get(0);
        return cObjOf(consent);
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
