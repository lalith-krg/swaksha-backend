package com.swaksha.hospitalservice.requests;

import com.swaksha.hospitalservice.entity.Consent;
import com.swaksha.hospitalservice.repository.ConsentRepo;
import com.swaksha.hospitalservice.repository.EhrRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public boolean save(RequestController.ConsentObj consentObj) {
        return updateConsentObj(consentOf(consentObj));
    }

    private boolean updateConsentObj(Consent consentObj){
        Consent consent = this.consentRepo.save(consentObj);

        if(consent == null){
            return false;
        }
        return true;
    }

    private RequestController.ConsentObj cObjOf(Consent consent){
        return new RequestController.ConsentObj(consent.getDoctorSSID(),
                consent.getHiuSSID(), consent.getPatientSSID(), consent.getHipSSID(),
                String.valueOf(consent.getDataAccessStartDate()), String.valueOf(consent.getDataAccessEndDate()),
                String.valueOf(consent.getRequestInitiatedDate()), String.valueOf(consent.getConsentApprovedDate()), String.valueOf(consent.getConsentEndDate()),
                consent.getConsentID(), consent.isSelfConsent(), consent.isApproved());
    }



    private Consent consentOf(RequestController.ConsentObj consentObj){
        return new Consent(consentObj.consentID(), LocalDate.parse(consentObj.consentEndDate()), consentObj.isApproved(),
                consentObj.selfConsent(),
                consentObj.doctorSSID(), consentObj.hiuSSID(), consentObj.patientSSID(), consentObj.hipSSID(),
                LocalDate.parse(consentObj.dataAccessStartDate()), LocalDate.parse(consentObj.dataAccessEndDate()), LocalDate.parse(consentObj.requestInitiatedDate()),
                LocalDate.parse(consentObj.consentApprovedDate()));
    }
}
