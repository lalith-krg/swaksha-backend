package com.swaksha.gatewayservice.request;


import com.swaksha.gatewayservice.firebase.controller.NotificationController;
import com.swaksha.gatewayservice.firebase.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gateway/request")
@CrossOrigin(origins= {"*"})

public class RequestController {

    private final RequestService requestService;

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                      String dataAccessStartDate, String dataAccessEndDate,
                      String requestInitiatedDate, String consentApprovedDate,
                      String consentEndDate, String consentID, boolean selfConsent, boolean isApproved){}


    record HiuRequestBody(String doctorSSID, String hiuSSID, String patientSSID,String hipSSID){}

    record OnHiuRequestBody(String response, String docSSID, String hiuSSID){}

    record HipRequestBody(ConsentObj consentObj, String dataPostUrl){}

    record OnHipRequestBody(String response){}

    record HiuRequestWithConsentBody(String docSSID, String hiuSSID, String patientSSID, ConsentObj consentObj, String dataPostUrl){}

    record HiuRequestWithConsentId(String consentId){}

    record ApproveConsentBody(String patientSSID, String encPin, ConsentObj consentObj){}

    record RevokeConsentBody (String consentID, String reqSSID) {}

    record ConsentObj1(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                       String dataAccessStartDate, String dataAccessEndDate,
                       String requestInitiatedDate, String consentApprovedDate,
                       String consentEndDate, String consentID, boolean selfConsent, boolean isApproved){}

    record ApproveConsentResponse(String response){}

    record VerifyConsentResponse(String response){}

    record VerifyConsentBody(String reqSSID, ConsentObj consentObj){}

    record OnApproveConsentBody(String response, ConsentObj consentObj){}

    record OnVerifyConsentBody(String response, String reqSSID, ConsentObj consentObj){}

    record OnFetchConsentsBody(String SSID, ArrayList<ConsentObj> consentObjs){}

    record OnFetchConsentsResponse(String response, String SSID, ArrayList<ConsentObj> consentObjs){}

    public record PatientSSIDBody(String patientSSID){}

    public record ConsentIdBody(String consentId){}

    // HIU places request for data
    record Notification(String token, String title, String body){}
    private final NotificationService notificationService;

    @PostMapping("/send")
    public void notif(@RequestBody Notification notification){
        this.notificationService.sendNotification(notification.token, notification.title, notification.body);
    }

    @PostMapping("/hiu/request")
    public HttpEntity<OnHiuRequestBody> hiuRequest(@RequestBody HiuRequestBody hiuRequestBody){

        System.out.println("hehe boi");

        // Check validity of all SSIDs

        System.out.println("DocSSID");
        System.out.println(hiuRequestBody.doctorSSID);
        System.out.println("hiuSSID");
        System.out.println(hiuRequestBody.hiuSSID);
        System.out.println("PatientSSID");
        System.out.println(hiuRequestBody.patientSSID);
        System.out.println("hip ssid");
        System.out.println(hiuRequestBody.hipSSID);

        boolean validity = this.requestService.validateSSID(hiuRequestBody.doctorSSID);

        System.out.println("val 1");
        System.out.println(validity);


//        if (!this.requestService.validateSSID(hiuRequestBody.hiuSSID))
//            validity = false;
        if (!this.requestService.validateSSID(hiuRequestBody.patientSSID))
            validity = false;

        System.out.println("hehe" + validity);

        if(!validity){
            // respond with invalid details

            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Invalid SSID",
                    hiuRequestBody.doctorSSID, hiuRequestBody.hiuSSID));
        }

    //    boolean saved = this.requestService.saveHiuLink(hipRequestBody.consentObj.hiuSSID, hipRequestBody.dataPostUrl);

        //hiu ssid must be extracted from API key .
        ConsentObj consentObj = new ConsentObj(hiuRequestBody.doctorSSID,"123456789",hiuRequestBody.patientSSID,hiuRequestBody.hipSSID,null,null,null,null,null,null,false,false);






        System.out.println("Reached here");


        // call /cm/consents/addPendingConsent
        String url = "http://localhost:9006/cm/consents/addPendingConsent";

        HttpEntity<ConsentObj> consentEntity = new HttpEntity<>(consentObj);
        System.out.println(consentEntity);
        ResponseEntity<Boolean> response = this.restTemplate.postForEntity(url, consentEntity, Boolean.class);

        if (Boolean.TRUE.equals(response.getBody()))
            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Consent Request Success", consentObj.doctorSSID, consentObj.hiuSSID));
        else
            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Consent Request Fail", consentObj.doctorSSID, consentObj.hiuSSID));

    }


    @PostMapping("/hiu/requestWithConsent")
    public HttpEntity<OnHiuRequestBody> hiuRequestWithConsent(@RequestBody HiuRequestWithConsentId hiuRequestWithConsentId){
        // similar to hiuRequest

        // VALIDATE HIU

        String url = "http://localhost:9006/cm/consents/fetchConsentById";
        ResponseEntity<ConsentObj> co_entity = this.restTemplate.postForEntity(url,
                new HttpEntity<>(new ConsentIdBody(hiuRequestWithConsentId.consentId)), ConsentObj.class);

        if(co_entity.getBody().consentID==null || !co_entity.getBody().isApproved){
            return new HttpEntity<>(new OnHiuRequestBody("Invalid consent", null, null));
        }

        HipRequestBody hipRequestBody = new HipRequestBody(co_entity.getBody(),
                this.requestService.getDataPostLink(Objects.requireNonNull(co_entity.getBody()).hiuSSID));
        HttpEntity<HipRequestBody> hrbEntity = new HttpEntity<>(hipRequestBody);

        HttpEntity<OnHipRequestBody> hip_re = hipSendRequest(Objects.requireNonNull(hrbEntity.getBody()));

        if(Objects.equals(Objects.requireNonNull(hip_re.getBody()).response, "Request Sent"))
            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Consent valid. Request placed.",
                    co_entity.getBody().doctorSSID, co_entity.getBody().hiuSSID));
        else
            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Consent valid. Request failed.",
                    co_entity.getBody().doctorSSID, co_entity.getBody().hiuSSID));

//        boolean validity = this.requestService.validateSSID(hiuRequestWithConsentBody.docSSID);
//
//        if (!this.requestService.validateSSID(hiuRequestWithConsentBody.hiuSSID))
//            validity = false;
//        if (!this.requestService.validateSSID(hiuRequestWithConsentBody.patientSSID))
//            validity = false;
//
//        if (!validity){
//
//            // respond with invalid details
//            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Invalid SSID",
//                    hiuRequestWithConsentBody.docSSID,
//                    hiuRequestWithConsentBody.hiuSSID));
//
//        }

        // check if consentObj is valid
        // call /gateway/request/verifyRequest
//        String url = "http://localhost:8999/cm/consents/verifyConsent";
//        VerifyConsentBody verifyConsentBody = new VerifyConsentBody(
//                hiuRequestWithConsentBody.docSSID, hiuRequestWithConsentBody.consentObj);
//        HttpEntity<VerifyConsentBody> consentEntity = new HttpEntity<>(verifyConsentBody);
//        ResponseEntity<VerifyConsentResponse> vc_re = this.restTemplate.postForEntity(url, consentEntity,
//                VerifyConsentResponse.class);

        // notify

//        if (Objects.requireNonNull(vc_re.getBody()).response.equals("Verified")){
//
//            // If verified place send request to hip
//            // call /gateway/request/hip/sendRequest
////            url = "http://localhost:8999/gateway/request/hip/sendRequest";
//
//            HipRequestBody hipRequestBody = new HipRequestBody(hiuRequestWithConsentBody.consentObj,
//                    hiuRequestWithConsentBody.dataPostUrl);
//            HttpEntity<HipRequestBody> hrbEntity = new HttpEntity<>(hipRequestBody);
////            ResponseEntity<OnHipRequestBody> sr_re = this.restTemplate.postForEntity(url, hrbEntity, OnHipRequestBody.class);
//
//            HttpEntity<OnHipRequestBody> sr_re = hipSendRequest(Objects.requireNonNull(hrbEntity.getBody()));
//
//            if(Objects.equals(Objects.requireNonNull(sr_re.getBody()).response, "Request Sent"))
//                return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Consent Verified. Request placed.", hiuRequestWithConsentBody.docSSID, hiuRequestWithConsentBody.hiuSSID));
//            else
//                return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Consent Verified. Request failed.", hiuRequestWithConsentBody.docSSID, hiuRequestWithConsentBody.hiuSSID));
//        }
//        else
//            return new HttpEntity<OnHiuRequestBody>(new OnHiuRequestBody("Invalid consent", hiuRequestWithConsentBody.docSSID, hiuRequestWithConsentBody.hiuSSID));

    }

    // Gateway requests HIP for data
    @PostMapping("/hip/sendRequest")
    public HttpEntity<OnHipRequestBody> hipSendRequest(@RequestBody HipRequestBody hipRequestBody){
        // call hip request
        // call /gateway/request/hip/sendRequest
        String url = this.requestService.getHipLink(hipRequestBody.consentObj.hipSSID) + "/hospital/requests/hip/sendRequest";
        ResponseEntity<OnHipRequestBody> sr_re = this.restTemplate.postForEntity(url,
                new HttpEntity<ConsentObj>(hipRequestBody.consentObj),
                OnHipRequestBody.class);

        // notify

        return new HttpEntity<OnHipRequestBody>(Objects.requireNonNull(sr_re.getBody()));

    }



    // the gateway service can request cm to verify the user pin to approve the consent
    @PostMapping("/approveConsent")
    public HttpEntity<ApproveConsentResponse> verifyConsentGateway(@RequestBody ApproveConsentBody approveConsentBody,Authentication authentication){

        // Check if all fields are filled and valid
        String ssid= authentication.getName();
        System.out.println(ssid);
        System.out.println(approveConsentBody.consentObj.dataAccessStartDate);
        System.out.println(approveConsentBody.consentObj.dataAccessEndDate);
        System.out.println(approveConsentBody.consentObj.consentID);
        ConsentObj consentObj=new ConsentObj(approveConsentBody.consentObj.doctorSSID,
                approveConsentBody.consentObj.hiuSSID, ssid, approveConsentBody.consentObj.hipSSID,
                approveConsentBody.consentObj.dataAccessStartDate, approveConsentBody.consentObj.dataAccessEndDate,
                approveConsentBody.consentObj.requestInitiatedDate, approveConsentBody.consentObj.consentApprovedDate,
                approveConsentBody.consentObj.consentEndDate, approveConsentBody.consentObj.consentID,approveConsentBody.consentObj.selfConsent, approveConsentBody.consentObj.isApproved);
        ApproveConsentBody approveConsentBody1=new ApproveConsentBody(ssid, approveConsentBody.encPin, consentObj);
        boolean validity = this.requestService.validateSSID(ssid);
     //   validity = validity && this.requestService.checkToApproveConsentFields(consentObj);
    //    validity = validity && (Objects.equals(ssid, approveConsentBody.consentObj.patientSSID));

        if (!validity){
            // fields invalid
            return  new HttpEntity<ApproveConsentResponse>(new ApproveConsentResponse("Invalid SSID"));
        }
        System.out.println(approveConsentBody1.consentObj.dataAccessStartDate);

        // Call /cm/consents/approveConsent
        String d= String.valueOf(approveConsentBody1.consentObj.dataAccessEndDate);
        System.out.println(d);
        String url = "http://localhost:9006/cm/consents/approveConsent";
        HttpEntity<ApproveConsentBody> consentEntity = new HttpEntity<>(approveConsentBody1);
        System.out.println(consentEntity.getBody().consentObj.dataAccessEndDate);
        ResponseEntity<OnApproveConsentBody> re = this.restTemplate.exchange(url, HttpMethod.POST,consentEntity,
                OnApproveConsentBody.class);


        System.out.println(re.getBody().response);
        if (Objects.requireNonNull(re.getBody()).response.equals("Approved")){

            HttpEntity<ConsentObj> co_entity = new HttpEntity<>(Objects.requireNonNull(re.getBody()).consentObj);

            String hiuUrl = this.requestService.getHipLink(Objects.requireNonNull(co_entity.getBody()).hiuSSID) + "/hospital/requests/consentUpdate";
            ResponseEntity<Boolean> hiu_re = this.restTemplate.postForEntity(hiuUrl, co_entity, Boolean.class);

            System.out.println("posted consent to hiu");

            // If verified place send request to hip
            // ask the HIP to send the data asked by HIU .. also send consent object along .

            //find the url of hip , by using its ssid .
            System.out.println(re.getBody().consentObj.hipSSID);
            System.out.println(approveConsentBody.consentObj.hipSSID);
            url = this.requestService.getHipLink(re.getBody().consentObj.hipSSID)+"/hospital/requests/hip/sendRequest";   // get url of hip



            HipRequestBody hipRequestBody = new HipRequestBody(re.getBody().consentObj,
                    this.requestService.getDataPostLink(re.getBody().consentObj.hiuSSID));// provide consent obj to hip along with call back url .

            HttpEntity<HipRequestBody> hrbEntity = new HttpEntity<HipRequestBody>(hipRequestBody);
    //            ResponseEntity<OnHipRequestBody> sr_re = this.restTemplate.postForEntity(url, hrbEntity,
    //                    OnHipRequestBody.class);

            System.out.println(url);
             ResponseEntity<String> sr_re = this.restTemplate.postForEntity(url,
                     hrbEntity,
                     String.class);

             System.out.println("reached HERE");

                 if(Objects.equals(Objects.requireNonNull(sr_re.getBody()), "data sent"))
                return new HttpEntity<ApproveConsentResponse>(new ApproveConsentResponse("Consent Approved. Request placed."));
            else
                return  new HttpEntity<ApproveConsentResponse>(new ApproveConsentResponse("Consent Approved. Request " +
                        "failed."));
        }

        return  new HttpEntity<ApproveConsentResponse>(new ApproveConsentResponse("done"));


    }

    // the gateway service can request cm to verify the consent object
    @PostMapping("/verifyConsent")
    public HttpEntity<VerifyConsentResponse> verifyConsentGateway(@RequestBody VerifyConsentBody verifyConsentBody){

        // Check if all fields are filled and valid
        String reqSSID = verifyConsentBody.reqSSID;

        System.out.println("Reached verify consent");

        boolean validity = this.requestService.validateSSID(reqSSID);

        if (!(Objects.equals(reqSSID, verifyConsentBody.consentObj().doctorSSID) ||
                Objects.equals(reqSSID, verifyConsentBody.consentObj().hiuSSID) ||
                Objects.equals(reqSSID, verifyConsentBody.consentObj().patientSSID) ||
                Objects.equals(reqSSID, verifyConsentBody.consentObj().hipSSID))){
            validity = false;
        }

        if (!validity){
            return new HttpEntity<VerifyConsentResponse>(new VerifyConsentResponse("Invalid SSID"));

        }

        String url = "http://localhost:9006/cm/consents/verifyConsent";
        HttpEntity<VerifyConsentBody> consentEntity = new HttpEntity<>(verifyConsentBody);
        ResponseEntity<OnVerifyConsentBody> re = this.restTemplate.postForEntity(url, consentEntity,
                OnVerifyConsentBody.class);

        // notify


        return new HttpEntity<VerifyConsentResponse>(new VerifyConsentResponse(Objects.requireNonNull(re.getBody()).response));

    }

    // Patient can request their active consents
    @PostMapping("/fetchConsents")
    public HttpEntity<OnFetchConsentsResponse> fetchConsents(Authentication authentication){
        // Check patientSSID
        String ssid= authentication.getName();
        System.out.println(ssid);
        boolean validity = this.requestService.validateSSID(ssid);


        if(!validity){
            // respond with invalid details


            return new HttpEntity<OnFetchConsentsResponse>(new OnFetchConsentsResponse("Invalid SSID", ssid,
                    new ArrayList<>()));
        }

        String url = "http://localhost:9006/cm/consents/fetchConsents";

        PatientSSIDBody patientSSIDBody=new PatientSSIDBody(ssid);

        HttpEntity<PatientSSIDBody> psBody = new HttpEntity<>(patientSSIDBody);
        ResponseEntity<OnFetchConsentsBody> re = this.restTemplate.postForEntity(url, psBody,
                OnFetchConsentsBody.class);

        return new HttpEntity<OnFetchConsentsResponse>(new OnFetchConsentsResponse("Valid",
                ssid, Objects.requireNonNull(re.getBody()).consentObjs));


    }

    // patient can request to revoke consents form CM
    @PostMapping("/revokeConsent")
    public HttpEntity<VerifyConsentResponse> revokeConsent(@RequestBody RevokeConsentBody revokeConsentBody, Authentication authentication){
        System.out.println("reached gateway");
        System.out.println(revokeConsentBody.consentID);
        System.out.println(revokeConsentBody.reqSSID);

        // Check patientSSID
        boolean validity = this.requestService.validateSSID(revokeConsentBody.reqSSID);

        if(!validity){
            // respond with invalid details
        }

        String url = "http://localhost:9006/cm/consents/revokeConsent";
        ResponseEntity<ConsentObj> re = this.restTemplate.postForEntity(url,
                new HttpEntity<>(revokeConsentBody), ConsentObj.class);

        HttpEntity<ConsentObj> co_entity = new HttpEntity<>(Objects.requireNonNull(re.getBody()));

        String hiuUrl = this.requestService.getHipLink(Objects.requireNonNull(co_entity.getBody()).hiuSSID) + "/hospital/requests/consentUpdate";
        ResponseEntity<Boolean> hiu_re = this.restTemplate.postForEntity(hiuUrl, co_entity, Boolean.class);

        String hipUrl = this.requestService.getHipLink(Objects.requireNonNull(co_entity.getBody()).hipSSID) + "/hospital/requests/consentUpdate";
        ResponseEntity<Boolean> hip_re = this.restTemplate.postForEntity(hipUrl, co_entity, Boolean.class);

        if(co_entity.getBody().isApproved)
            return new HttpEntity<>(new VerifyConsentResponse("Negative"));
        return new HttpEntity<>(new VerifyConsentResponse("Revoked"));


        // notify
    }

    // reject Consent
    @PostMapping("/rejectConsent")
    public HttpEntity<VerifyConsentResponse> rejectConsent(@RequestBody RevokeConsentBody revokeConsentBody){
        // Check patientSSID
        boolean validity = true;

        if (!this.requestService.validateSSID(revokeConsentBody.reqSSID))
            validity = false;

        if(!validity){
            // respond with invalid details
        }

        String url = "http://localhost:9006/cm/consents/fetchConsentById";
        ResponseEntity<ConsentObj> co_entity = this.restTemplate.postForEntity(url,
                new HttpEntity<>(new ConsentIdBody(revokeConsentBody.consentID)), ConsentObj.class);

        url = "http://localhost:9006/cm/consents/rejectConsent";
        ResponseEntity<Boolean> re = this.restTemplate.postForEntity(url,
                new HttpEntity<>(revokeConsentBody), Boolean.class);

        // notify

        String hiuUrl = this.requestService.getHipLink(Objects.requireNonNull(co_entity.getBody()).hiuSSID) + "/hospital/requests/deleteConsent";
        ResponseEntity<Boolean> hiu_re = this.restTemplate.postForEntity(hiuUrl,
                new HttpEntity<>(revokeConsentBody.consentID), Boolean.class);

        String hipUrl = this.requestService.getHipLink(Objects.requireNonNull(co_entity.getBody()).hipSSID) + "/hospital/requests/deleteConsent";
        ResponseEntity<Boolean> hip_re = this.restTemplate.postForEntity(hipUrl,
                new HttpEntity<>(revokeConsentBody.consentID), Boolean.class);

        if(re.getBody())
            return new HttpEntity<>(new VerifyConsentResponse("Negative"));
        return new HttpEntity<>(new VerifyConsentResponse("Revoked"));
    }



}
