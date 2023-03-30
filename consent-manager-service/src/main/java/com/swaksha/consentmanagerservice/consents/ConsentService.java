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
    public boolean verifyConsent(Consent consentObj) {

        boolean validity = true;

        // search consent object by consent ID
        Consent consent = searchConsentObjWithConsentID(consentObj.getConsentID());
//        ArrayList<Consent> consents = (ArrayList<Consent>) this.consentRepo.findByConsentID(consentObj.getConsentID());
//
//        if(consents.size()<1){
//            return false;
//        }
//
//        Consent consent = consents.get(0);
//
//        if (consent == null)
//            return false;

        // check every field in consent object
        if (!Objects.equals(consentObj, consent))
            validity = false;

        return validity;
    }

    @Autowired
    public Consent approveConsent(Consent consentObj) {

        // generate consent ID
        String consentId = generateConsentID();
//        String consentId = UUID.randomUUID().toString();

        consentObj.setConsentID(consentId);
        consentObj.setApproved(true);
        consentObj.setConsentApprovedTime(LocalDateTime.now());

//        consentObj.setConsentEndTime(LocalDateTime.now());
//        consentObj.setDataAccessStartTime(LocalDateTime.now());
//        consentObj.setDataAccessEndTime(LocalDateTime.now());
//        consentObj.setRequestInitiatedTime(LocalDateTime.now());
//
//        consentObj.setDoctorSSID("");
//        consentObj.setHiuSSID("");
//        consentObj.setPatientSSID("");
//        consentObj.setHipSSID("");

        // add the new consentObj to records
        boolean res = addNewConsentObj(consentObj);
//        Consent consent = this.consentRepo.save(consentObj);

//        if(consent == null){
////            return blankConsent();
//            new Consent(null, LocalDateTime.now(), false, false,
//                    null, null, null, null, LocalDateTime.now(),
//                    LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
//        }

        return consentObj;
    }

//    @Autowired
    public ArrayList<Consent> fetchConsents(String patientSSID) {
        // search for records with patientSSID
        // return records with patientSSID
//        return searchConsentObjWithSSID(patientSSID);
        ArrayList<Consent> consents = (ArrayList<Consent>) this.consentRepo.findByPatientSSID(patientSSID);
//        ArrayList<Consent> consents = new ArrayList<Consent>();
        return consents;
    }


    private String generateConsentID(){
        return UUID.randomUUID().toString();
    }

    private Consent searchConsentObjWithConsentID(String consentID){
        ArrayList<Consent> consents = (ArrayList<Consent>) this.consentRepo.findByConsentID(consentID);

        if(consents.size()<1){
            return blankConsent();
        }

        Consent consent = consents.get(0);

        if (consent == null)
            return blankConsent();

        else
            return consent;
    }

    private ArrayList<Consent> searchConsentObjWithSSID(String SSID){
        ArrayList<Consent> consents = (ArrayList<Consent>) this.consentRepo.findByPatientSSID(SSID);

        return consents;
    }

    @Autowired
    public boolean addPendingConsent(Consent consentObj) {

        String consentId = generateConsentID();
//        String consentId = UUID.randomUUID().toString();

        consentObj.setConsentID(consentId);

//        Consent consent = this.consentRepo.save(consentObj);
//
//        if(consent == null)
//            return false;
//
//        return true;

        return addNewConsentObj(consentObj);
    }


    private boolean addNewConsentObj(Consent consentObj){
        Consent consent = this.consentRepo.save(consentObj);

        if(consent == null)
            return false;

        return true;
    }

    @Autowired
    public boolean revokeConsent(Consent consentObj) {
        this.consentRepo.delete(consentObj);

        return true;
    }

//
//    private ConsentController.ConsentObj blankConsentObj(){
//        return new ConsentController.ConsentObj(null, null, null, null,
//                null, null, null, null, null,
//                null, false, false);
//    }


    @Autowired
    private Consent blankConsent(){
        return new Consent(null, null, false, false,
                null, null, null, null, null,
                null, null, null);
    }


//    private ConsentController.ConsentObj cObjOf(Consent consent){
//        return new ConsentController.ConsentObj(consent.getDoctorSSID(),
//                    consent.getHiuSSID(), consent.getPatientSSID(), consent.getHipSSID(),
//                    consent.getDataAccessStartTime(), consent.getDataAccessEndTime(),
//                    consent.getRequestInitiatedTime(), consent.getConsentApprovedTime(), consent.getConsentEndTime(),
//                    consent.getConsentID(), consent.isSelfConsent(), consent.isApproved());
//    }
//
//
//    private Consent consentOf(ConsentController.ConsentObj consentObj){
//        return new Consent(consentObj.consentID(), consentObj.consentEndTime(), consentObj.isApproved(), consentObj.selfConsent(),
//                consentObj.doctorSSID(), consentObj.hiuSSID(), consentObj.patientSSID(), consentObj.hipSSID(),
//                consentObj.dataAccessStartTime(), consentObj.dataAccessEndTime(), consentObj.requestInitiatedTime(),
//                consentObj.consentApprovedTime());
//    }
}
