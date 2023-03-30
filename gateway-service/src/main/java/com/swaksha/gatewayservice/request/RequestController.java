package com.swaksha.gatewayservice.request;

import net.minidev.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/gateway/request")
public class RequestController {

    private final RequestService requestService = new RequestService();
    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                      LocalDateTime dataAccessStartTime, LocalDateTime dataAccessEndTime,
                      LocalDateTime requestInitiatedTime, LocalDateTime consentApprovedTime,
                      LocalDateTime consentEndTime, String consentID, boolean selfConsent, boolean isApproved){}

    record HiuRequestBody(String docSSID, String hiuSSID, String patientSSID){}

    record HiuRequestWithConsentBody(String docSSID, String hiuSSID, String patientSSID, ConsentObj consentObj){}

    record ApproveConsentBody(String patientSSID, String encPin, ConsentObj consentObj){}

    record VerifyConsentBody(String reqSSID, ConsentObj consentObj){}

    record OnApproveConsentBody(String response, ConsentObj consentObj){}

    record OnVerifyConsentBody(String response, String reqSSID, ConsentObj consentObj){}

    record OnFetchConsentsBody(String SSID, ArrayList<ConsentObj> consentObjs){}

    // HIU places request for data
    @PostMapping("/hiu/request")
    public void hiuRequest(@RequestBody HiuRequestBody hiuRequestBody){
        // Check validity of all SSIDs

        boolean validity = true;

        if (!this.requestService.validateSSID(hiuRequestBody.docSSID))
            validity = false;
        if (!this.requestService.validateSSID(hiuRequestBody.hiuSSID))
            validity = false;
        if (!this.requestService.validateSSID(hiuRequestBody.patientSSID))
            validity = false;

        if(!validity){
            // respond with invalid details
        }

        ConsentObj consentObj = new ConsentObj(hiuRequestBody.docSSID, hiuRequestBody.hiuSSID, hiuRequestBody.patientSSID, null, null, null, LocalDateTime.now(), null, null, null, false, false);

        // call /gateway/patient/getConsent
        // String url = "http://localhost:8999/gateway/patient/getConsent";
        // HttpEntity<ConsentObj> consentEntity = new HttpEntity<>(consentObj);
        // this.restTemplate.postForEntity(url, consentEntity, Boolean.class);

        // call /cm/consents/addPendingConsent
    }

    // HIU places request for data with prior consent
    @PostMapping("/hiu/requestWithConsent")
    public void hiuRequestWithConsent(@RequestBody HiuRequestWithConsentBody hiuRequestWithConsentBody){
        // similar to hiuRequest

        boolean validity = true;

        if (!this.requestService.validateSSID(hiuRequestWithConsentBody.docSSID))
            validity = false;
        if (!this.requestService.validateSSID(hiuRequestWithConsentBody.hiuSSID))
            validity = false;
        if (!this.requestService.validateSSID(hiuRequestWithConsentBody.patientSSID))
            validity = false;

        if(!validity){
            // respond with invalid details
        }

        // check if consentObj is valid
        // call /gateway/request/verifyRequest
        String url = "http://localhost:8999/cm/consents/verifyConsent";
        VerifyConsentBody verifyConsentBody = new VerifyConsentBody(
                hiuRequestWithConsentBody.docSSID, hiuRequestWithConsentBody.consentObj);
        HttpEntity<VerifyConsentBody> consentEntity = new HttpEntity<>(verifyConsentBody);
        ResponseEntity<OnVerifyConsentBody> vc_re = this.restTemplate.postForEntity(url, consentEntity,
                OnVerifyConsentBody.class);

        // If verified place send request to hip
        // call /gateway/request/hip/sendRequest
        url = "http://localhost:8999/gateway/request/hip/sendRequest";
        ResponseEntity<String> sr_re = this.restTemplate.postForEntity(url, hiuRequestWithConsentBody.consentObj, String.class);

        // notify
    }

    // Gateway requests HIP for data
    @PostMapping("/hip/sendRequest")
    public String hipSendRequest(@RequestBody ConsentObj consentObj){
        // call hip request

        // notify
        return "Sample text";
    }

    // the gateway service can request cm to verify the user pin to approve the consent
    @PostMapping("/approveConsent")
    public void verifyConsentGateway(@RequestBody ApproveConsentBody approveConsentBody){
        // Check if all fields are filled and valid
        boolean validity = this.requestService.validateSSID(approveConsentBody.patientSSID);
        validity = validity && this.requestService.checkToApproveConsentFields(approveConsentBody.consentObj);

//        if(!validity){
//            // fields invalid
//        }

        // Call /cm/consents/approveConsent
        String url = "http://localhost:8999/cm/consents/approveConsent";
        HttpEntity<ApproveConsentBody> consentEntity = new HttpEntity<>(approveConsentBody);
        ResponseEntity<OnApproveConsentBody> re = this.restTemplate.postForEntity(url, consentEntity,
                OnApproveConsentBody.class);

        // If verified place send request to hip
        // call /gateway/request/hip/sendRequest
        url = "http://localhost:8999/gateway/request/hip/sendRequest";
        ResponseEntity<String> sr_re = this.restTemplate.postForEntity(url, Objects.requireNonNull(re.getBody()).consentObj, String.class);

        // notify
    }

    // the gateway service can request cm to verify the consent object
    @PostMapping("/verifyConsent")
    public String verifyConsentGateway(@RequestBody VerifyConsentBody verifyConsentBody){
        // Check if all fields are filled and valid
        String reqSSID = verifyConsentBody.reqSSID;

        boolean validity = this.requestService.validateSSID(reqSSID);

        if (!(Objects.equals(reqSSID, verifyConsentBody.consentObj().doctorSSID) ||
                Objects.equals(reqSSID, verifyConsentBody.consentObj().hiuSSID) ||
                Objects.equals(reqSSID, verifyConsentBody.consentObj().patientSSID) ||
                Objects.equals(reqSSID, verifyConsentBody.consentObj().hipSSID))){
            validity = false;
        }

//        if(!validity){
//            // fields invalid
//        }

        // Call /cm/consents/verifyConsent
        // HttpResponse httpResponse = verifyConsentGateway(new VerifyConsentBody(hiuRequestWithConsentBody.docSSID,hiuRequestWithConsentBody.consentObj));

        String url = "http://localhost:8999/cm/consents/verifyConsent";
        HttpEntity<VerifyConsentBody> consentEntity = new HttpEntity<>(verifyConsentBody);
        ResponseEntity<OnVerifyConsentBody> re = this.restTemplate.postForEntity(url, consentEntity,
                OnVerifyConsentBody.class);

        return re.getBody().reqSSID;

        // notify
    }

    // Patient can request their active consents
    @PostMapping("/fetchConsents")
    public ArrayList<ConsentObj> fetchConsents(@RequestBody String patientSSID){
        // Check patientSSID
        boolean validity = true;

        if (!this.requestService.validateSSID(patientSSID))
            validity = false;

        if(!validity){
            // respond with invalid details
        }

        String url = "http://localhost:8999/cm/consents/fetchConsents";
        ResponseEntity<OnFetchConsentsBody> re = this.restTemplate.postForEntity(url, patientSSID, OnFetchConsentsBody.class);

        return re.getBody().consentObjs;
    }

    // patient can request to revoke consents form CM
    @PostMapping("/revokeConsent")
    public void revokeConsent(@RequestBody VerifyConsentBody verifyConsentBody){
        // Check patientSSID
        boolean validity = true;

        if (!this.requestService.validateSSID(verifyConsentBody.reqSSID))
            validity = false;

        if(!validity){
            // respond with invalid details
        }
        String url = "http://localhost:8999/cm/consents/revokeConsent";
        ResponseEntity<Boolean> re = this.restTemplate.postForEntity(url, verifyConsentBody.reqSSID, Boolean.class);

        // notify
    }

    // reject Consent
    @PostMapping("/rejectConsent")
    public void rejectConsent(@RequestBody VerifyConsentBody verifyConsentBody){
        // Check patientSSID
        boolean validity = true;

        if (!this.requestService.validateSSID(verifyConsentBody.reqSSID))
            validity = false;

        if(!validity){
            // respond with invalid details
        }
        String url = "http://localhost:8999/cm/consents/rejectConsent";
        ResponseEntity<Boolean> re = this.restTemplate.postForEntity(url, verifyConsentBody.reqSSID, Boolean.class);

        // notify
    }

    // Notification
    @PostMapping("/notify")
    public void notify(String notiType, JSONObject[] jsonObjs){
        // Check type of notification and respond accordingly
    }

    /*
    // CM's response to verify consent
    @PostMapping("/onApproveConsent")
    public void onApproveConsentGateway(@RequestBody OnApproveConsentBody onApproveConsentBody){
        // Check response type

        // If any problem, re-initiate consent request from patient
        // Call /gateway/patient/getConsent

        // Otherwise place send request to hip
        // call /gateway/request/hip/sendRequest

        // notify
    }

    // CM's response to verify consent
    @PostMapping("/onVerifyConsent")
    public void onVerifyConsentGateway(@RequestBody OnVerifyConsentBody onVerifyConsentBody){
        // Check response type

        // notify
    }
    */

    // Consent account details


    /*
    // Request Manager's reply to consent request by hiu
    @PostMapping("/hiu/on-request")
    public void hiuRequestOnRequest(String SSID){
        //
    }

    // HIU places consent request
    @PostMapping("/hiu/confirm-request")
    public void hiuConfirmRequest(String SSID){
        //
    }

    // HIP fetches consent request
    @PostMapping("/hip/fetch")
    public void hipFetchRequestRequest(String SSID){
        //
    }

    // HIP consent notification
    @PostMapping("/hip/on-notify")
    public void hipRequestNotify(String SSID){
        //
    }

    // HIU requesting status on consent
    @PostMapping("/hiu/status")
    public void hiuRequestStatus(String SSID){
        //
    }

    // Request Manager's reply to consent request
    @PostMapping("/hiu/on-status")
    public void hiuRequestOnStatus(String SSID){
        //
    }
     */
}
