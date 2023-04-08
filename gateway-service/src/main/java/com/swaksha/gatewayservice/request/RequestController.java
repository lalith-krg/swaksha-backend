package com.swaksha.gatewayservice.request;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gateway/request")
public class RequestController {

    private final RequestService requestService;
    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                      LocalDate dataAccessStartDate, LocalDate dataAccessEndDate,
                      LocalDate requestInitiatedDate, LocalDate consentApprovedDate,
                      LocalDate consentEndDate, String consentID, boolean selfConsent, boolean isApproved){}

//<<<<<<< HEAD
//    record HiuRequestBody(String docSSID, String hiuSSID, String patientSSID){}
//
//    record OnHiuRequestBody(String response, String docSSID, String hiuSSID){}
//
//    record OnHipRequestBody(String response){}
//
//    record HiuRequestWithConsentBody(String docSSID, String hiuSSID, String patientSSID, ConsentObj consentObj){}
//=======
    record HiuRequestBody(String docSSID, String hiuSSID, String patientSSID, String dataPostUrl){}

    record OnHiuRequestBody(String response, String docSSID, String hiuSSID){}

    record HipRequestBody(ConsentObj consentObj, String dataPostUrl){}

    record OnHipRequestBody(String response){}

    record HiuRequestWithConsentBody(String docSSID, String hiuSSID, String patientSSID, ConsentObj consentObj, String dataPostUrl){}
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb

    record ApproveConsentBody(String patientSSID, String encPin, ConsentObj consentObj){}

    record ApproveConsentResponse(String response){}

    record VerifyConsentResponse(String response){}

    record VerifyConsentBody(String reqSSID, ConsentObj consentObj){}

    record OnApproveConsentBody(String response, ConsentObj consentObj){}

    record OnVerifyConsentBody(String response, String reqSSID, ConsentObj consentObj){}

    record OnFetchConsentsBody(String SSID, ArrayList<ConsentObj> consentObjs){}

    record OnFetchConsentsResponse(String response, String SSID, ArrayList<ConsentObj> consentObjs){}

    public record PatientSSIDBody(String patientSSID){}

    // HIU places request for data
    @PostMapping("/hiu/request")
//<<<<<<< HEAD
//    public OnHiuRequestBody hiuRequest(@RequestBody HiuRequestBody hiuRequestBody){
//=======
    public HttpEntity<OnHiuRequestBody> hiuRequest(@RequestBody HiuRequestBody hiuRequestBody){
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
        // Check validity of all SSIDs

        boolean validity = this.requestService.validateSSID(hiuRequestBody.docSSID);

        if (!this.requestService.validateSSID(hiuRequestBody.hiuSSID))
            validity = false;
        if (!this.requestService.validateSSID(hiuRequestBody.patientSSID))
            validity = false;

        if(!validity){
            // respond with invalid details
//<<<<<<< HEAD
//            return new OnHiuRequestBody("Invalid SSID", hiuRequestBody.docSSID, hiuRequestBody.hiuSSID);
//        }
//
//=======
            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Invalid SSID",
                    hiuRequestBody.docSSID, hiuRequestBody.hiuSSID));
        }

        boolean saved = this.requestService.saveHiuLink(hiuRequestBody.hiuSSID, hiuRequestBody.dataPostUrl);

//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
        ConsentObj consentObj = new ConsentObj(hiuRequestBody.docSSID, hiuRequestBody.hiuSSID, hiuRequestBody.patientSSID, null, null, null, LocalDate.now(), null, null, null, false, false);

        // call /gateway/patient/getConsent
        // String url = "http://localhost:8999/gateway/patient/getConsent";
        // HttpEntity<ConsentObj> consentEntity = new HttpEntity<>(consentObj);
        // this.restTemplate.postForEntity(url, consentEntity, Boolean.class);

        // call /cm/consents/addPendingConsent
        String url = "http://localhost:8999/cm/consents/addPendingConsent";
//<<<<<<< HEAD
//        ResponseEntity<Boolean> response = this.restTemplate.postForEntity(url, consentObj, Boolean.class);
//
//        if (Boolean.TRUE.equals(response.getBody()))
//            return new OnHiuRequestBody("Consent Request Success", hiuRequestBody.docSSID, hiuRequestBody.hiuSSID);
//        else
//            return new OnHiuRequestBody("Consent Request Fail", hiuRequestBody.docSSID, hiuRequestBody.hiuSSID);
//=======
        HttpEntity<ConsentObj> consentEntity = new HttpEntity<>(consentObj);
        ResponseEntity<Boolean> response = this.restTemplate.postForEntity(url, consentEntity, Boolean.class);

        if (Boolean.TRUE.equals(response.getBody()))
            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Consent Request Success", hiuRequestBody.docSSID, hiuRequestBody.hiuSSID));
        else
            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Consent Request Fail", hiuRequestBody.docSSID, hiuRequestBody.hiuSSID));
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
    }

    // HIU places request for data with prior consent
    @PostMapping("/hiu/requestWithConsent")
//<<<<<<< HEAD
//    public OnHiuRequestBody hiuRequestWithConsent(@RequestBody HiuRequestWithConsentBody hiuRequestWithConsentBody){
//=======
    public HttpEntity<OnHiuRequestBody> hiuRequestWithConsent(@RequestBody HiuRequestWithConsentBody hiuRequestWithConsentBody){
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
        // similar to hiuRequest

        boolean validity = this.requestService.validateSSID(hiuRequestWithConsentBody.docSSID);

        if (!this.requestService.validateSSID(hiuRequestWithConsentBody.hiuSSID))
            validity = false;
        if (!this.requestService.validateSSID(hiuRequestWithConsentBody.patientSSID))
            validity = false;

        if (!validity){
            // respond with invalid details
//<<<<<<< HEAD
//            return new OnHiuRequestBody("Invalid SSID", hiuRequestWithConsentBody.docSSID, hiuRequestWithConsentBody.hiuSSID);
//=======
            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Invalid SSID",
                    hiuRequestWithConsentBody.docSSID,
                    hiuRequestWithConsentBody.hiuSSID));
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
        }

        // check if consentObj is valid
        // call /gateway/request/verifyRequest
        String url = "http://localhost:8999/cm/consents/verifyConsent";
        VerifyConsentBody verifyConsentBody = new VerifyConsentBody(
                hiuRequestWithConsentBody.docSSID, hiuRequestWithConsentBody.consentObj);
        HttpEntity<VerifyConsentBody> consentEntity = new HttpEntity<>(verifyConsentBody);
        ResponseEntity<VerifyConsentResponse> vc_re = this.restTemplate.postForEntity(url, consentEntity,
                VerifyConsentResponse.class);

        // notify

        if (Objects.requireNonNull(vc_re.getBody()).response.equals("Verified")){

            // If verified place send request to hip
            // call /gateway/request/hip/sendRequest
            url = "http://localhost:8999/gateway/request/hip/sendRequest";
//<<<<<<< HEAD
//            ResponseEntity<OnHipRequestBody> sr_re = this.restTemplate.postForEntity(url, hiuRequestWithConsentBody.consentObj, OnHipRequestBody.class);
//
//            if(Objects.equals(Objects.requireNonNull(sr_re.getBody()).response, "Request Sent"))
//                return new OnHiuRequestBody("Consent Verified. Request placed.", hiuRequestWithConsentBody.docSSID,
//                        hiuRequestWithConsentBody.hiuSSID);
//            else
//                return new OnHiuRequestBody("Consent Verified. Request failed.", hiuRequestWithConsentBody.docSSID,
//                        hiuRequestWithConsentBody.hiuSSID);
//        }
//        else
//            return new OnHiuRequestBody("Invalid consent", hiuRequestWithConsentBody.docSSID,
//                    hiuRequestWithConsentBody.hiuSSID);
//=======
            HipRequestBody hipRequestBody = new HipRequestBody(hiuRequestWithConsentBody.consentObj,
                    hiuRequestWithConsentBody.dataPostUrl);
            HttpEntity<HipRequestBody> hrbEntity = new HttpEntity<>(hipRequestBody);
            ResponseEntity<OnHipRequestBody> sr_re = this.restTemplate.postForEntity(url, hrbEntity,
                    OnHipRequestBody.class);

            if(Objects.equals(Objects.requireNonNull(sr_re.getBody()).response, "Request Sent"))
                return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Consent Verified. Request placed.", hiuRequestWithConsentBody.docSSID, hiuRequestWithConsentBody.hiuSSID));
            else
                return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Consent Verified. Request failed.", hiuRequestWithConsentBody.docSSID, hiuRequestWithConsentBody.hiuSSID));
        }
        else
            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Invalid consent", hiuRequestWithConsentBody.docSSID, hiuRequestWithConsentBody.hiuSSID));
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
    }

    // Gateway requests HIP for data
    @PostMapping("/hip/sendRequest")
//<<<<<<< HEAD
//    public HttpEntity<OnFetchConsentsResponse> hipSendRequest(@RequestBody ConsentObj consentObj){
//        // call hip request
//
//        // notify
//        return new OnHipRequestBody("Request Placed");
//=======
    public HttpEntity<OnHipRequestBody> hipSendRequest(@RequestBody HipRequestBody hipRequestBody){
        // call hip request
        // call /gateway/request/hip/sendRequest
        String url = "http://localhost:8999/hospital/requests/hip/sendRequest";
        ResponseEntity<OnHipRequestBody> sr_re = this.restTemplate.postForEntity(url,
                new HttpEntity<ConsentObj>(hipRequestBody.consentObj),
                OnHipRequestBody.class);

        // notify
//        return new OnHipRequestBody("Request Placed");
        return new HttpEntity<OnHipRequestBody>(Objects.requireNonNull(sr_re.getBody()));
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
    }

    // the gateway service can request cm to verify the user pin to approve the consent
    @PostMapping("/approveConsent")
//<<<<<<< HEAD
//    public ApproveConsentResponse verifyConsentGateway(@RequestBody ApproveConsentBody approveConsentBody){
//=======
    public HttpEntity<ApproveConsentResponse> verifyConsentGateway(@RequestBody ApproveConsentBody approveConsentBody){
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
        // Check if all fields are filled and valid
        boolean validity = this.requestService.validateSSID(approveConsentBody.patientSSID);
        validity = validity && this.requestService.checkToApproveConsentFields(approveConsentBody.consentObj);
        validity = validity && (Objects.equals(approveConsentBody.patientSSID, approveConsentBody.consentObj.patientSSID));

        if (!validity){
            // fields invalid
//<<<<<<< HEAD
//            return new ApproveConsentResponse("Invalid SSID");
//=======
            return  new HttpEntity<ApproveConsentResponse>(new ApproveConsentResponse("Invalid SSID"));
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
        }

        // Call /cm/consents/approveConsent
        String url = "http://localhost:8999/cm/consents/approveConsent";
        HttpEntity<ApproveConsentBody> consentEntity = new HttpEntity<>(approveConsentBody);
        ResponseEntity<OnApproveConsentBody> re = this.restTemplate.postForEntity(url, consentEntity,
                OnApproveConsentBody.class);

        // If verified place send request to hip
        // call /gateway/request/hip/sendRequest
        // notify

        if (Objects.requireNonNull(re.getBody()).response.equals("Approved")){

            // If verified place send request to hip
            // call /gateway/request/hip/sendRequest
            url = "http://localhost:8999/gateway/request/hip/sendRequest";
//<<<<<<< HEAD
////            ResponseEntity<OnHipRequestBody> sr_re = this.restTemplate.postForEntity(url,
////                    Objects.requireNonNull(re.getBody()).consentObj, OnHipRequestBody.class);
//////
//////            if(Objects.equals(Objects.requireNonNull(sr_re.getBody()).response, "Request Sent"))
//////                return new ApproveConsentResponse("Consent Approved. Request placed.");
//////            else
//////                return new ApproveConsentResponse("Consent Approved. Request failed.");
//////        }
//////        else
//////            return new ApproveConsentResponse("Consent rejected");
//////=======
            HipRequestBody hipRequestBody = new HipRequestBody(re.getBody().consentObj,
                    this.requestService.getHiuLink(re.getBody().consentObj.hiuSSID));
            HttpEntity<HipRequestBody> hrbEntity = new HttpEntity<HipRequestBody>(hipRequestBody);
            ResponseEntity<OnHipRequestBody> sr_re = this.restTemplate.postForEntity(url, hrbEntity,
                    OnHipRequestBody.class);

            if(Objects.equals(Objects.requireNonNull(sr_re.getBody()).response, "Request Sent"))
                return new HttpEntity<ApproveConsentResponse>(new ApproveConsentResponse("Consent Approved. Request placed."));
            else
                return  new HttpEntity<ApproveConsentResponse>(new ApproveConsentResponse("Consent Approved. Request " +
                        "failed."));
        }
        else
            return  new HttpEntity<ApproveConsentResponse>(new ApproveConsentResponse("Consent rejected"));
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
    }

    // the gateway service can request cm to verify the consent object
    @PostMapping("/verifyConsent")
//<<<<<<< HEAD
//    public VerifyConsentResponse verifyConsentGateway(@RequestBody VerifyConsentBody verifyConsentBody){
//=======
    public HttpEntity<VerifyConsentResponse> verifyConsentGateway(@RequestBody VerifyConsentBody verifyConsentBody){
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
        // Check if all fields are filled and valid
        String reqSSID = verifyConsentBody.reqSSID;

        boolean validity = this.requestService.validateSSID(reqSSID);

        if (!(Objects.equals(reqSSID, verifyConsentBody.consentObj().doctorSSID) ||
                Objects.equals(reqSSID, verifyConsentBody.consentObj().hiuSSID) ||
                Objects.equals(reqSSID, verifyConsentBody.consentObj().patientSSID) ||
                Objects.equals(reqSSID, verifyConsentBody.consentObj().hipSSID))){
            validity = false;
        }

        if (!validity){
//<<<<<<< HEAD
//            return new VerifyConsentResponse("Invalid SSID");
//=======
            return new HttpEntity<VerifyConsentResponse>(new VerifyConsentResponse("Invalid SSID"));
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb
        }

        String url = "http://localhost:8999/cm/consents/verifyConsent";
        HttpEntity<VerifyConsentBody> consentEntity = new HttpEntity<>(verifyConsentBody);
        ResponseEntity<OnVerifyConsentBody> re = this.restTemplate.postForEntity(url, consentEntity,
                OnVerifyConsentBody.class);

        // notify

//<<<<<<< HEAD
//        return new VerifyConsentResponse(Objects.requireNonNull(re.getBody()).response);
//=======
        return new HttpEntity<VerifyConsentResponse>(new VerifyConsentResponse(Objects.requireNonNull(re.getBody()).response));
//>>>>>>> 97a42a60e0194b7840564736af085be8435ce9eb

    }

    // Patient can request their active consents
    @PostMapping("/fetchConsents")

    public HttpEntity<OnFetchConsentsResponse> fetchConsents(@RequestBody PatientSSIDBody patientSSIDBody, Authentication authentication){
        // Check patientSSID
        String ssid= authentication.getName();

        boolean validity = this.requestService.validateSSID(patientSSIDBody.patientSSID);

        if(!validity){
            // respond with invalid details

            return new HttpEntity<OnFetchConsentsResponse>(new OnFetchConsentsResponse("Invalid SSID", "",
                    new ArrayList<>()));
        }

        String url = "http://localhost:8999/cm/consents/fetchConsents";
        HttpEntity<PatientSSIDBody> psBody = new HttpEntity<>(patientSSIDBody);
        ResponseEntity<OnFetchConsentsBody> re = this.restTemplate.postForEntity(url, psBody,
                OnFetchConsentsBody.class);

        return new HttpEntity<OnFetchConsentsResponse>(new OnFetchConsentsResponse("Valid",
                patientSSIDBody.patientSSID, Objects.requireNonNull(re.getBody()).consentObjs));

    }

    // patient can request to revoke consents form CM
    @PostMapping("/revokeConsent")
    public void revokeConsent(@RequestBody VerifyConsentBody verifyConsentBody){
        // Check patientSSID
        boolean validity = this.requestService.validateSSID(verifyConsentBody.reqSSID);

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
//    @PostMapping("/notify")
//    public void notify(String notiType, JSONObject[] jsonObjs){
//        // Check type of notification and respond accordingly
//    }

}
