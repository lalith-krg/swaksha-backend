package com.swaksha.consentmanagerservice.consents;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/cm/consents")
public class ConsentController {

    // the gateway service can verify the user pin to approve the consent
    @PostMapping("/approveConsent")
    public void verifyConsentCM(String patientSSID, String encPin, JSONObject consentObj){
        // Check if SSID is legit

        // Verify the consent object is legit and reqSSID is associated with the co

        // Verify that the patient pin is valid
        // call /cm/patient/auth/verifyPin

        // Respond to /gateway/request/onApproveConsent
    }

    // the gateway service can verify the consent object
    @PostMapping("/verifyConsent")
    public void verifyConsentCM(String reqSSID, JSONObject consentObj){
        // Check if Requesting SSID is legit

        // Verify the consent object is legit and reqSSID is associated with the co

        // Respond to /gateway/request/onVerifyConsent
    }

    // the gateway can fetch consents from cm
    @PostMapping("/fetchConsents")
    public void fetchConsentsCM(String SSID){
        // search for consents associated with SSID
    }

    // patient can request to revoke consents form CM
    @PostMapping("/revokeConsent")
    public void fetchConsentsCM(String SSID, JSONObject consentObj){
        // search and erase consent object if exists

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
