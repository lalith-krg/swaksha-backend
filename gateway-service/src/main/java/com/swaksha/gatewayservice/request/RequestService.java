package com.swaksha.gatewayservice.request;

public class RequestService {
    public boolean validateSSID(String SSID) {
        return true;
    }

    public boolean checkToApproveConsentFields(RequestController.ConsentObj consentObj) {
        boolean validity = true;

        if(consentObj.selfConsent() != true){
            validity = validateSSID(consentObj.doctorSSID()) && validateSSID(consentObj.hiuSSID());
        }
        validity = validateSSID(consentObj.patientSSID()) && validateSSID(consentObj.hipSSID());
        if(!validity){
            return false;
        }

        if(consentObj.consentEndTime() == null || consentObj.requestInitiatedTime() == null)
            return false;

        if(consentObj.requestInitiatedTime().isAfter(consentObj.consentEndTime()))
            validity = false;

        return validity;
    }
}
