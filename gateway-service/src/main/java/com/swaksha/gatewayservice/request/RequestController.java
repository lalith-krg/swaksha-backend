package com.swaksha.gatewayservice.request;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway/request")
public class RequestController {

    // HIU places request for data
    @PostMapping("/hiu/request")
    public void hiuRequest(String docSSID, String hiuSSID, String patientSSID){
        // Check validity of both SSIDs

        // call /gateway/patient/getConsent
    }

    // HIU places request for data with prior consent
    @PostMapping("/hiu/requestWithConsent")
    public void hiuRequestWithConsent(String docSSID, String hiuSSID, String patientSSID, JSONObject consentObj){
        // similar to hiuRequest

        // check if consentObj is valid
        // call /gateway/request/verifyRequest

        // If verified place send request to hip
        // call /gateway/request/hip/sendRequest

        // notify
    }

    // Gateway requests HIP for data
    @PostMapping("/hip/sendRequest")
    public void hipSendRequest(@RequestBody JSONObject consentObj){
        // call hip request

        // notify
    }

    // the gateway service can request cm to verify the user pin to approve the consent
    @PostMapping("/approveConsent")
    public void verifyConsentGateway(String patientSSID, String encPin, JSONObject consentObj){
        // Check if all fields are filled and valid

        // Call /cm/consents/approveConsent

        // notify
    }

    // the gateway service can request cm to verify the consent object
    @PostMapping("/verifyConsent")
    public void verifyConsentGateway(String reqSSID, JSONObject consentObj){
        // Check if all fields are filled and valid

        // Call /cm/consents/verifyConsent

        // notify
    }

    // CM's response to verify consent
    @PostMapping("/onApproveConsent")
    public void onApproveConsentGateway(String response, JSONObject consentObj){
        // Check response type

        // If any problem, re-initiate consent request from patient
        // Call /gateway/patient/getConsent

        // Otherwise place send request to hip
        // call /gateway/request/hip/sendRequest

        // notify
    }

    // CM's response to verify consent
    @PostMapping("/onVerifyConsent")
    public void onVerifyConsentGateway(String response){
        // Check response type

        // notify
    }

    // Notification
    @PostMapping("/notify")
    public void notify(String notiType, JSONObject[] jsonObjs){
        // Check type of notification and respond accordingly
    }


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
