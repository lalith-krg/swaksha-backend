package com.swaksha.consentmanagerservice.consents;

import com.swaksha.consentmanagerservice.entity.Consent;
import com.swaksha.consentmanagerservice.repository.ConsentRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        // search consent object by consent ID
        Consent consent = searchConsentObjWithConsentID(consentObj.getConsentID());

        // check every field in consent object
        if (!(Objects.equals(consentObj.getConsentID(), consent.getConsentID())))
            return false;

        if (!(Objects.equals(consentObj.getDoctorSSID(), consent.getDoctorSSID())))
            return false;

        if (!(Objects.equals(consentObj.getHiuSSID(), consent.getHiuSSID())))
            return false;

        if (!(Objects.equals(consentObj.getPatientSSID(), consent.getPatientSSID())))
            return false;

        if (!(Objects.equals(consentObj.getHipSSID(), consent.getHipSSID())))
            return false;

        if (!Objects.equals(consentObj.getConsentApprovedDate(), consent.getConsentApprovedDate()))
            return false;

        if (!Objects.equals(consentObj.getConsentEndDate(), consent.getConsentEndDate()))
            return false;

        if (!Objects.equals(consentObj.getDataAccessStartDate(), consent.getDataAccessStartDate()))
            return false;

        if (!Objects.equals(consentObj.getDataAccessEndDate(), consent.getDataAccessEndDate()))
            return false;

        if (!Objects.equals(consentObj.isSelfConsent(), consent.isSelfConsent()))
            return false;

        if (!(consentObj.isApproved() == consent.isApproved()))
            return false;

        if(consent.getConsentEndDate() != null && LocalDate.now().isAfter(consent.getConsentEndDate())){
            return false;
        }

        return true;
    }

    @Autowired
    public Consent approveConsent(Consent consentObj) {

        String consentId = consentObj.getConsentID();

        // check if consent obj already exists
        if (consentId == null || consentId == ""){
            // generate consent ID
            consentId = generateConsentID();
            consentObj.setConsentID(consentId);
        }

        consentObj.setApproved(true);
        consentObj.setConsentApprovedDate(LocalDate.now());

        if(consentObj.getConsentEndDate() != null && LocalDate.now().isAfter(consentObj.getConsentEndDate())){
            return blankConsent();
        }
        // add the new consentObj to records
        boolean res = addNewConsentObj(consentObj);

        if(res){
            return consentObj;
        }
        return blankConsent();
    }

    public ArrayList<Consent> fetchConsents(String patientSSID) {
        // search for records with patientSSID
        // return records with patientSSID
        ArrayList<Consent> consents = (ArrayList<Consent>) this.consentRepo.findByPatientSSID(patientSSID);

        return consents;
    }

    public Consent searchConsentObjWithConsentID(String consentID){
        ArrayList<Consent> consents = (ArrayList<Consent>) this.consentRepo.findByConsentID(consentID);

        if(consents.size()<1){
            return blankConsent();
        }

        Consent consent = consents.get(0);

        if(consent.getConsentEndDate() != null && LocalDate.now().isAfter(consent.getConsentEndDate())){
            return blankConsent();
        }
        if(consent == null){
            return blankConsent();
        }
        return consent;
    }

    public boolean addPendingConsent(Consent consentObj) {
        String consentId = generateConsentID();

        consentObj.setConsentID(consentId);
        consentObj.setRequestInitiatedDate(LocalDate.now());

        return addNewConsentObj(consentObj);
    }

    private boolean addNewConsentObj(Consent consentObj){
        Consent consent = this.consentRepo.save(consentObj);

        if(consent == null){
            return false;
        }
        return true;
    }

    public Consent revokeConsent(Consent consent) {
        Consent revokedConsent = new Consent(consent.getConsentID(), consent.getConsentEndDate(),
                false, consent.isSelfConsent(),
                consent.getDoctorSSID(), consent.getHiuSSID(), consent.getPatientSSID(), consent.getHipSSID(),
                consent.getDataAccessStartDate(), consent.getDataAccessEndDate(),
                consent.getRequestInitiatedDate(), consent.getConsentApprovedDate());

        return this.consentRepo.save(revokedConsent);
    }

    public boolean rejectConsent(Consent consent) {
        this.consentRepo.delete(consent);
        return true;
    }

    private Consent blankConsent(){
        return new Consent(null, null, false, false,
                null, null, null, null, null,
                null, null, null);
    }

    private String generateConsentID(){
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private void printConsent(Consent consent){
        System.out.println("ConsentID: " + consent.getConsentID());
        System.out.println("Approved: " + consent.isApproved() + "\t\tSelf consent: " + consent.isSelfConsent());
        System.out.println("Doctor: " + consent.getDoctorSSID() + "\t\tHIU: " + consent.getHiuSSID());
        System.out.println("Patient: " + consent.getPatientSSID() + "\t\tHIP: " + consent.getHipSSID());
        System.out.print("DataStart: "); System.out.print(consent.getDataAccessStartDate());
        System.out.print("\t\tDataEnd: "); System.out.println(consent.getDataAccessEndDate());
        System.out.print("RequestInit: "); System.out.print(consent.getRequestInitiatedDate());
        System.out.print("\t\tConsentApproved: "); System.out.println(consent.getConsentApprovedDate());
        System.out.println("ConsentEnd: " + consent.getConsentEndDate());
    }
}