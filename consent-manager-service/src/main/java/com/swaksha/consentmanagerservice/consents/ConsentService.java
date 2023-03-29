package com.swaksha.consentmanagerservice.consents;

import com.swaksha.consentmanagerservice.entity.Consent;
import com.swaksha.consentmanagerservice.repository.ConsentRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ConsentService {

    private ConsentRepo consentRepo;

    public boolean verifyConsent(ConsentController.ConsentObj consentObj) {

        boolean validity = true;

        // search consent object by consent ID
        ConsentController.ConsentObj existingObj = searchConsentObjWithConsentID(consentObj.consentID());

        // check every field in consent object
        if (!Objects.equals(consentObj, existingObj))
            validity = false;

        return validity;
    }

    public ConsentController.ConsentObj approveConsent(ConsentController.ConsentObj consentObj) {

        // generate consent ID
        String consentId = generateConsentID();

        ConsentController.ConsentObj newConsentObj = new ConsentController.ConsentObj(
                consentObj.doctorSSID(), consentObj.hiuSSID(), consentObj.patientSSID(), consentObj.hipSSID(),
                consentObj.dataAccessStartTime(), consentObj.dataAccessEndTime(), consentObj.requestInitiatedTime(),
                LocalDateTime.now(), consentObj.consentEndTime(), consentId, consentObj.selfConsent()
        );

        // add the new consentObj to records
        boolean res = addNewConsentObj(newConsentObj);

        if(!res){
            return new ConsentController.ConsentObj(null, null, null, null,
                    null, null, null, null, null,
                    null, false);
        }

        return newConsentObj;
    }

    public ArrayList<ConsentController.ConsentObj> fetchConsents(String patientSSID) {
        // search for records with patientSSID
        // return records with patientSSID
        return searchConsentObjWithSSID(patientSSID);
    }

    private String generateConsentID(){
        return UUID.randomUUID().toString();
    }

    private ConsentController.ConsentObj searchConsentObjWithConsentID(String consentID){
//        Consent consent = this.consentRepo.searchConsentByID(consentID);

//        if (consent == null)
            return new ConsentController.ConsentObj(null, null, null, null,
                null, null, null, null, null,
                null, false);

//        else
//            return new ConsentController.ConsentObj(consent.getDoctorSSID(), consent.getHiuSSID(), consent.getPatientSSID(),
//                    consent.getHipSSID(), consent.getDataAccessStartTime(), consent.getDataAccessEndTime(),
//                    consent.getRequestInitiatedTime(), consent.getConsentApprovedTime(), consent.getConsentEndTime(),
//                    consent.getConsentID(), consent.isSelfConsent());
    }

    private ArrayList<ConsentController.ConsentObj> searchConsentObjWithSSID(String SSID){
//        ArrayList<Consent> consents = (ArrayList<Consent>) this.consentRepo.searchConsentBySSID(SSID);

        ArrayList<ConsentController.ConsentObj> consentObjs = new ArrayList<ConsentController.ConsentObj>();

//        for(Consent consent: consents){
//            ConsentController.ConsentObj consentObj = new ConsentController.ConsentObj(consent.getDoctorSSID(),
//                    consent.getHiuSSID(), consent.getPatientSSID(), consent.getHipSSID(),
//                    consent.getDataAccessStartTime(), consent.getDataAccessEndTime(),
//                    consent.getRequestInitiatedTime(), consent.getConsentApprovedTime(), consent.getConsentEndTime(),
//                    consent.getConsentID(), consent.isSelfConsent());
//            consentObjs.add(consentObj);
//        }

        return consentObjs;
    }

    private boolean addNewConsentObj(ConsentController.ConsentObj consentObj){
        Consent consent = new Consent(consentObj.consentID(), consentObj.consentEndTime(), consentObj.selfConsent(),
                consentObj.doctorSSID(), consentObj.hiuSSID(), consentObj.patientSSID(), consentObj.hipSSID(),
                consentObj.dataAccessStartTime(), consentObj.dataAccessEndTime(), consentObj.requestInitiatedTime(),
                consentObj.consentApprovedTime());

        consent = this.consentRepo.save(consent);

        if(consent == null)
            return false;

        return true;
    }

    public boolean revokeConsent(ConsentController.ConsentObj consentObj) {
        Consent consent = new Consent(consentObj.consentID(), consentObj.consentEndTime(), consentObj.selfConsent(),
                consentObj.doctorSSID(), consentObj.hiuSSID(), consentObj.patientSSID(), consentObj.hipSSID(),
                consentObj.dataAccessStartTime(), consentObj.dataAccessEndTime(), consentObj.requestInitiatedTime(),
                consentObj.consentApprovedTime());

        this.consentRepo.delete(consent);

        return true;
    }
}
