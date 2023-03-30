package com.swaksha.consentmanagerservice.consents;

import com.swaksha.consentmanagerservice.entity.Consent;
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

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cm/consents")
public class ConsentController {

    private final ConsentService consentService;

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    public record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                      LocalDateTime dataAccessStartTime, LocalDateTime dataAccessEndTime,
                      LocalDateTime requestInitiatedTime, LocalDateTime consentApprovedTime,
                      LocalDateTime consentEndTime, String consentID, boolean selfConsent, boolean isApproved){
    }
    public record ApproveConsentBody(String patientSSID, String encPin, ConsentObj consentObj){}

    public record VerifyConsentBody(String reqSSID, ConsentObj consentObj){}

    public record OnApproveConsentBody(String response, ConsentObj consentObj){}

    public record OnVerifyConsentBody(String response, String reqSSID, ConsentObj consentObj){}

    public record OnFetchConsentsBody(String SSID, ArrayList<ConsentObj> consentObjs){}

    public record PinToVerifyBody(String SSID, String encPin){}

    // the gateway service can verify the user pin to approve the consent
    @PostMapping("/approveConsent")
    public HttpEntity<OnApproveConsentBody> verifyConsentCM(@RequestBody ApproveConsentBody approveConsentBody){
        // Verify that the patient pin is valid
        // call /cm/patient/auth/verifyPin
        String url = "http://localhost:8999/cm/patient/auth/verifyPin";
        HttpEntity<PinToVerifyBody> entity = new HttpEntity<>(new PinToVerifyBody(
                approveConsentBody.patientSSID, approveConsentBody.encPin
        ));
        ResponseEntity<Boolean> re = this.restTemplate.postForEntity(url, entity, Boolean.class);

        // Respond to /gateway/request/onApproveConsent
        String returnUrl = "http://localhost:8999/gateway/request/onApproveConsent";

        if(!re.getBody()){
            HttpEntity<OnApproveConsentBody> approveEntity = new HttpEntity<>(new OnApproveConsentBody(
                    "Rejected", approveConsentBody.consentObj
            ));
            // this.restTemplate.postForEntity(returnUrl, approveEntity, Boolean.class);
            // return;
            return approveEntity;
        }

        // create final consent object
        Consent newConsentObj = this.consentService.approveConsent(consentOf(approveConsentBody.consentObj));
        ConsentObj consentObj = cObjOf(newConsentObj);

        HttpEntity<OnApproveConsentBody> approveEntity = new HttpEntity<>(new OnApproveConsentBody(
                "Approved", consentObj
        ));
        // this.restTemplate.postForEntity(returnUrl, approveEntity, Boolean.class);
        return approveEntity;
    }

    // the gateway service can verify the consent object
    @PostMapping("/verifyConsent")
    public HttpEntity<OnVerifyConsentBody> verifyConsentCM(@RequestBody VerifyConsentBody verifyConsentBody){
        // Verify the consent object is legit and reqSSID is associated with the co
        boolean validity = this.consentService.verifyConsent(consentOf(verifyConsentBody.consentObj));

        // Respond to /gateway/request/onVerifyConsent
        String returnUrl = "http://localhost:8999/gateway/request/onVerifyConsent";

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
    public HttpEntity<OnFetchConsentsBody> fetchConsentsCM(@RequestBody String patientSSID){
        // search for consents associated with SSID
        ArrayList<Consent> consents = this.consentService.fetchConsents(patientSSID);

        ArrayList<ConsentController.ConsentObj> consentObjs = new ArrayList<ConsentController.ConsentObj>();

        for(Consent consent: consents){
            ConsentController.ConsentObj consentObj = cObjOf(consent);
            consentObjs.add(consentObj);
        }

        // return consentObjs
        // String returnUrl = "http://localhost:8999/gateway/request/onFetchConsents";
        HttpEntity<OnFetchConsentsBody> fetchEntity = new HttpEntity<>(new OnFetchConsentsBody(
                patientSSID, consentObjs
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
    @PostMapping("/addPendingConsents")
    public boolean addPendingConsents(@RequestBody ConsentObj consentObj){
        // save consentObj
        return this.consentService.addPendingConsent(consentOf(consentObj));
    }


    private ConsentObj cObjOf(Consent consent){
        return new ConsentObj(consent.getDoctorSSID(),
                consent.getHiuSSID(), consent.getPatientSSID(), consent.getHipSSID(),
                consent.getDataAccessStartTime(), consent.getDataAccessEndTime(),
                consent.getRequestInitiatedTime(), consent.getConsentApprovedTime(), consent.getConsentEndTime(),
                consent.getConsentID(), consent.isSelfConsent(), consent.isApproved());
    }


    private Consent consentOf(ConsentObj consentObj){
        return new Consent(consentObj.consentID(), consentObj.consentEndTime(), consentObj.isApproved(), consentObj.selfConsent(),
                consentObj.doctorSSID(), consentObj.hiuSSID(), consentObj.patientSSID(), consentObj.hipSSID(),
                consentObj.dataAccessStartTime(), consentObj.dataAccessEndTime(), consentObj.requestInitiatedTime(),
                consentObj.consentApprovedTime());
    }

}
