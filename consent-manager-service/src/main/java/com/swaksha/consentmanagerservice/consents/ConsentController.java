package com.swaksha.consentmanagerservice.consents;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
@RequestMapping("/cm/consents")
public class ConsentController {

    private final ConsentService consentService = new ConsentService();

    record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                      LocalDateTime dataAccessStartTime, LocalDateTime dataAccessEndTime,
                      LocalDateTime requestInitiatedTime, LocalDateTime consentApprovedTime,
                      LocalDateTime consentEndTime, String consentID, boolean selfConsent){
    }
    record ApproveConsentBody(String patientSSID, String encPin, ConsentObj consentObj){}

    record VerifyConsentBody(String reqSSID, ConsentObj consentObj){}

    record OnApproveConsentBody(String response, ConsentObj consentObj){}

    record OnVerifyConsentBody(String response, String reqSSID, ConsentObj consentObj){}

    // the gateway service can verify the user pin to approve the consent
    @PostMapping("/approveConsent")
    public void verifyConsentCM(@RequestBody ApproveConsentBody approveConsentBody){
        // Verify that the patient pin is valid
        // call /cm/patient/auth/verifyPin

        // create final consent object
        ConsentObj newConsentObj = this.consentService.approveConsent(approveConsentBody.consentObj);

        // Respond to /gateway/request/onApproveConsent
    }

    // the gateway service can verify the consent object
    @PostMapping("/verifyConsent")
    public void verifyConsentCM(@RequestBody VerifyConsentBody verifyConsentBody){
        // Verify the consent object is legit and reqSSID is associated with the co
        boolean validity = this.consentService.verifyConsent(verifyConsentBody.consentObj);

        // Respond to /gateway/request/onVerifyConsent
    }

    // the gateway can fetch consents from cm
    @PostMapping("/fetchConsents")
    public void fetchConsentsCM(@RequestBody String patientSSID){
        // search for consents associated with SSID
        ArrayList<ConsentObj> consentObjs = this.consentService.fetchConsents(patientSSID);

        // return consentObjs
    }

    // patient can request to revoke consents form CM
    @PostMapping("/revokeConsent")
    public void fetchConsentsCM(@RequestBody ConsentObj consentObj){
        // search and erase consent object if exists
        boolean revoked = this.consentService.revokeConsent(consentObj);

        // return success or failure
    }

    /*
    // HIU places consent request
    @PostMapping("/hiu/request")
    public void hiuConsentRequest(String SSID){
        //
    }

    // Consent Manager's reply to consent request by hiu
    @PostMapping("/hiu/on-request")
    public void hiuConsentOnRequest(String SSID){
        //
    }

    // HIU places consent request
    @PostMapping("/hiu/confirm-request")
    public void hiuConfirmRequest(String SSID){
        //
    }

    // HIU requesting status on consent
    @PostMapping("/hiu/status")
    public void hiuConsentStatus(String SSID){
        //
    }

    // Consent Manager's reply to consent request
    @PostMapping("/hiu/on-status")
    public void hiuConsentOnStatus(String SSID){
        //
    }

    // HIP fetches consent request
    @PostMapping("/hip/fetch")
    public void hipFetchConsentRequest(String SSID){
        //
    }

    // HIP consent notification
    @PostMapping("/hip/on-notify")
    public void hipConsentNotify(String SSID){
        //
    }

    // HIU consent notification
    @PostMapping("/hiu/on-notify")
    public void hiuConsentNotify(String SSID){
        //
    }
    */

}
