package com.swaksha.consentmanagerservice.consents;

import com.swaksha.consentmanagerservice.entity.Consent;
import com.swaksha.consentmanagerservice.repository.ConsentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Service
@Component
@RequiredArgsConstructor
public class ConsentService {

    @Autowired
    private final ConsentRepo consentRepo;

    @Autowired
    public boolean verifyConsent(ConsentController.ConsentObj consentObj) {

        boolean validity = true;

        // search consent object by consent ID
        ConsentController.ConsentObj existingObj = searchConsentObjWithConsentID(consentObj.consentID().toString());

        // check every field in consent object
        if (!Objects.equals(consentObj, existingObj))
            validity = false;

        return validity;
    }

    @Autowired
    public ConsentController.ConsentObj approveConsent(ConsentController.ConsentObj consentObj) {

        // generate consent ID
        String consentId = generateConsentID();

        ConsentController.ConsentObj newConsentObj = new ConsentController.ConsentObj(
                consentObj.doctorSSID(), consentObj.hiuSSID(), consentObj.patientSSID(), consentObj.hipSSID(),
                consentObj.dataAccessStartTime(), consentObj.dataAccessEndTime(), consentObj.requestInitiatedTime(),
                LocalDateTime.now(), consentObj.consentEndTime(), consentId, consentObj.selfConsent(), true
        );

        // add the new consentObj to records
        boolean res = addNewConsentObj(newConsentObj);

        if(!res){
            return blankConsentObj();
        }

        return newConsentObj;
    }

    @Autowired
    public ArrayList<ConsentController.ConsentObj> fetchConsents(String patientSSID) {
        // search for records with patientSSID
        // return records with patientSSID
        return searchConsentObjWithSSID(patientSSID);
    }


    private String generateConsentID(){
        return UUID.randomUUID().toString();
    }

    @Autowired
    private ConsentController.ConsentObj searchConsentObjWithConsentID(String consentID){
        ArrayList<Consent> consents = (ArrayList<Consent>) this.consentRepo.findByConsentID(consentID);

        if(consents.size()<1){
            return blankConsentObj();
        }

        Consent consent = consents.get(0);

        if (consent == null)
            return blankConsentObj();

        else
            return cObjOf(consent);
    }

    private ArrayList<ConsentController.ConsentObj> searchConsentObjWithSSID(String SSID){
        ArrayList<Consent> consents = (ArrayList<Consent>) this.consentRepo.findByPatientSSID(SSID);

        ArrayList<ConsentController.ConsentObj> consentObjs = new ArrayList<ConsentController.ConsentObj>();

        for(Consent consent: consents){
            ConsentController.ConsentObj consentObj = cObjOf(consent);
            consentObjs.add(consentObj);
        }

        return consentObjs;
    }

    @Autowired
    public boolean addPendingConsent(ConsentController.ConsentObj consentObj) {
        return addNewConsentObj(consentObj);
    }


    private boolean addNewConsentObj(ConsentController.ConsentObj consentObj){
        Consent consent = this.consentRepo.save(consentOf(consentObj));

        if(consent == null)
            return false;

        return true;
    }

    @Autowired
    public boolean revokeConsent(ConsentController.ConsentObj consentObj) {
        Consent consent = consentOf(consentObj);

        this.consentRepo.delete(consent);

        return true;
    }


    private ConsentController.ConsentObj blankConsentObj(){
        return new ConsentController.ConsentObj(null, null, null, null,
                null, null, null, null, null,
                null, false, false);
    }


    private ConsentController.ConsentObj cObjOf(Consent consent){
        return new ConsentController.ConsentObj(consent.getDoctorSSID(),
                    consent.getHiuSSID(), consent.getPatientSSID(), consent.getHipSSID(),
                    consent.getDataAccessStartTime(), consent.getDataAccessEndTime(),
                    consent.getRequestInitiatedTime(), consent.getConsentApprovedTime(), consent.getConsentEndTime(),
                    consent.getConsentID(), consent.isSelfConsent(), consent.isApproved());
    }


    private Consent consentOf(ConsentController.ConsentObj consentObj){
        return new Consent(consentObj.consentID(), consentObj.consentEndTime(), consentObj.isApproved(), consentObj.selfConsent(),
                consentObj.doctorSSID(), consentObj.hiuSSID(), consentObj.patientSSID(), consentObj.hipSSID(),
                consentObj.dataAccessStartTime(), consentObj.dataAccessEndTime(), consentObj.requestInitiatedTime(),
                consentObj.consentApprovedTime());
    }
}
