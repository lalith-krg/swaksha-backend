package com.swaksha.consentmanagerservice.consents.controller;

import com.swaksha.consentmanagerservice.consents.entity.Consent;
import com.swaksha.consentmanagerservice.consents.service.ConsentService;
import com.swaksha.consentmanagerservice.patientAuth.service.PatientService;



import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cm/consents")
public class ConsentController {

    private final ConsentService consentService;
    private final PatientService patientService;

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    public record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                      LocalDate dataAccessStartDate, LocalDate dataAccessEndDate,
                      LocalDate requestInitiatedDate, LocalDate consentApprovedDate,
                      LocalDate consentEndDate, String consentID, boolean selfConsent, boolean isApproved){
    }
    public record ApproveConsentBody(String patientSSID, String encPin, ConsentObj consentObj){}
    public record VerifyConsentBody(String reqSSID, ConsentObj consentObj){}
    public record OnApproveConsentBody(String response, ConsentObj consentObj){}
    public record OnVerifyConsentBody(String response, String reqSSID, ConsentObj consentObj){}
    public record OnFetchConsentsBody(String SSID, ArrayList<ConsentObj> consentObjs){}
    public record PinToVerifyBody(String SSID, String encPin){}
    public record PatientSSIDBody(String patientSSID){}

    // the gateway service can verify the user pin to approve the consent
    @PostMapping("/approveConsent")
    public HttpEntity<OnApproveConsentBody> verifyConsentCM(@RequestBody ApproveConsentBody approveConsentBody){
        // Verify that the patient pin is valid
        // call /cm/patient/auth/verifyPin

//        String url = "http://localhost:9006/cm/patient/auth/verifyPin";
//
//        HttpEntity<PinToVerifyBody> entity = new HttpEntity<>(new PinToVerifyBody(
//                approveConsentBody.patientSSID, approveConsentBody.encPin
//        ));
//        ResponseEntity<Boolean> re = this.restTemplate.postForEntity(url, entity, Boolean.class);

        boolean re = this.patientService.verifyPin(approveConsentBody.patientSSID, approveConsentBody.encPin);

        // Respond to /gateway/request/onApproveConsent

        System.out.println("consent object-");
        System.out.println(re);

        if(!re){
            HttpEntity<OnApproveConsentBody> approveEntity = new HttpEntity<>(new OnApproveConsentBody(
                    "Rejected", approveConsentBody.consentObj
            ));
            return approveEntity;
        }

        // create final consent object
        Consent newConsentObj = this.consentService.approveConsent(consentOf(approveConsentBody.consentObj));
        ConsentObj consentObj = cObjOf(newConsentObj);

        HttpEntity<OnApproveConsentBody> approveEntity = new HttpEntity<>(new OnApproveConsentBody(
                "Approved", consentObj
        ));
        return approveEntity;
    }

    // the gateway service can verify the consent object
    @PostMapping("/verifyConsent")
    public HttpEntity<OnVerifyConsentBody> verifyConsentCM(@RequestBody VerifyConsentBody verifyConsentBody){
        // Verify the consent object is legit and reqSSID is associated with the co
        boolean validity = this.consentService.verifyConsent(consentOf(verifyConsentBody.consentObj));

        // Respond to /gateway/request/onVerifyConsent
        String returnUrl = "http://localhost:9005/gateway/request/onVerifyConsent";

        if(!validity) {
            HttpEntity<OnVerifyConsentBody> verifyEntity = new HttpEntity<>(new OnVerifyConsentBody(
                    "Invalid", verifyConsentBody.reqSSID, verifyConsentBody.consentObj
            ));
            // this.restTemplate.postForEntity(returnUrl, verifyEntity, Boolean.class);
            // return;
            return verifyEntity;
        }

        HttpEntity<OnVerifyConsentBody> verifyEntity = new HttpEntity<>(new OnVerifyConsentBody(
                "Verified", verifyConsentBody.reqSSID, verifyConsentBody.consentObj
        ));
        // this.restTemplate.postForEntity(returnUrl, verifyEntity, Boolean.class);
        return verifyEntity;
    }

    // the gateway can fetch consents from cm
    @PostMapping("/fetchConsents")
    public HttpEntity<OnFetchConsentsBody> fetchConsentsCM(@RequestBody PatientSSIDBody patientSSIDBody){
        // search for consents associated with SSID
        ArrayList<Consent> consents = this.consentService.fetchConsents(patientSSIDBody.patientSSID);

        ArrayList<ConsentObj> consentObjs = new ArrayList<>();

        for(Consent consent: consents){
            ConsentObj consentObj = cObjOf(consent);
            consentObjs.add(consentObj);
        }

        // return consentObjs
        // String returnUrl = "http://localhost:8999/gateway/request/onFetchConsents";
        HttpEntity<OnFetchConsentsBody> fetchEntity = new HttpEntity<>(new OnFetchConsentsBody(
                patientSSIDBody.patientSSID, consentObjs
        ));
        // this.restTemplate.postForEntity(returnUrl, fetchEntity, Boolean.class);
        return fetchEntity;
    }

    // patient can request to revoke consents form CM
    @PostMapping("/revokeConsent")
    public boolean revokeConsents(@RequestBody ConsentObj consentObj){
        // search and erase consent object if exists
        boolean revoked = this.consentService.revokeConsent(consentOf(consentObj));

        // return success or failure
        return revoked;
    }

    // reject Consent
    @PostMapping("/rejectConsent")
    public boolean rejectConsent(@RequestBody ConsentObj consentObj){
        // search and erase consent object if exists
        boolean revoked = this.consentService.revokeConsent(consentOf(consentObj));

        // return success or failure
        return revoked;
    }

    // add pending consents
    @PostMapping("/addPendingConsent")
    public boolean addPendingConsent(@RequestBody ConsentObj consentObj){
        // save consentObj
        System.out.println("hello from consent");
        System.out.println(consentObj.patientSSID);

        return this.consentService.addPendingConsent(consentOf(consentObj));
    }


    private ConsentObj cObjOf(Consent consent){
        return new ConsentObj(consent.getDoctorSSID(),
                consent.getHiuSSID(), consent.getPatientSSID(), consent.getHipSSID(),
                consent.getDataAccessStartDate(), consent.getDataAccessEndDate(),
                consent.getRequestInitiatedDate(), consent.getConsentApprovedDate(), consent.getConsentEndDate(),
                consent.getConsentID(), consent.isSelfConsent(), consent.isApproved());
    }


    private Consent consentOf(ConsentObj consentObj){
        return new Consent(consentObj.consentID(), consentObj.consentEndDate(), consentObj.isApproved(),
                consentObj.selfConsent(),
                consentObj.doctorSSID(), consentObj.hiuSSID(), consentObj.patientSSID(), consentObj.hipSSID(),
                consentObj.dataAccessStartDate(), consentObj.dataAccessEndDate(), consentObj.requestInitiatedDate(),
                consentObj.consentApprovedDate());
    }

}
