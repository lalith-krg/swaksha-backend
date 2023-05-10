package com.swaksha.hospitalservice.requests;

import com.swaksha.hospitalservice.entity.Ehr;
import com.swaksha.hospitalservice.repository.EhrRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hospital/requests")
@RestController
@CrossOrigin(origins= {"*"})
public class RequestController {

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    private final RequestService requestService;

    private final EhrRepo ehrRepo;
    public record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                             String dataAccessStartDate, String dataAccessEndDate,
                             String requestInitiatedDate, String consentApprovedDate,
                             String consentEndDate, String consentID, boolean selfConsent, boolean isApproved) {
    }

    record HiuPlaceRequest(String patientSSID){}

//    record SendRequestedData(String data,String patientSSID){}

    record SendRequestedData(LocalDate creationDate, String patientSSID,
                             String type, String observationCode, String observationValue,
                             String conditionCode, String procedureCode){}

    record HiuPlaceRequestWithConsent(String docSSID, String patientSSID, String consentID){}

    record HiuRequestBody(String docSSID, String hiuSSID, String patientSSID, String dataPostUrl) {}

    record HiuRequestWithConsent(String docSSID, String hiuSSID, String patientSSID, ConsentObj consentObj, String dataPostUrl){}

    record OnHiuRequestBody(String response, String docSSID, String hiuSSID){}

    record HipRequestBody(ConsentObj consentObj, String dataPostUrl){}

    record VerifyConsentResponse(String response){}

    record VerifyConsentBody(String reqSSID, ConsentObj consentObj){}

    record OnVerifyConsentBody(String response, String reqSSID, ConsentObj consentObj){}

    @PostMapping("/demo")
    public String demo(){
        return "hello !";
    }
    @PostMapping("/newRequest")
    public HttpEntity<OnHiuRequestBody> hiuRequest(@RequestBody HiuPlaceRequest hiuPlaceRequest, Authentication authentication) {
            String ssid= authentication.getName();
//        System.out.println(hiuPlaceRequest.docSSID);
//        System.out.println(hiuPlaceRequest.patientSSID);
        // call /gateway/request/hiu/request
        String url = "http://localhost:9005/gateway/request/hiu/request";

        HttpEntity<HiuRequestBody> reqEntity = new HttpEntity<>(new HiuRequestBody(ssid,
                "123456789", hiuPlaceRequest.patientSSID, "URL"));

        ResponseEntity<OnHiuRequestBody> ohr = this.restTemplate.postForEntity(url, reqEntity, OnHiuRequestBody.class);

        return new ResponseEntity<>(ohr.getBody(), HttpStatus.OK);
    }

    @PostMapping("/requestWithConsent")
    public void hiuRequestWithConsentBody(@RequestBody HiuPlaceRequestWithConsent hiuPlaceRequestWithConsent){

        // call /gateway/request/hiu/requestWithConsent
        String url = "http://localhost:8999/gateway/request/hiu/requestWithConsent";

        HttpEntity<HiuRequestWithConsent> reqEntity =
                new HttpEntity<>(new HiuRequestWithConsent(hiuPlaceRequestWithConsent.docSSID, "hiussid",
                        hiuPlaceRequestWithConsent.patientSSID,
                        this.requestService.findConsentWithID(hiuPlaceRequestWithConsent.consentID), "URL"));

        ResponseEntity<OnHiuRequestBody> ohr = this.restTemplate.postForEntity(url, reqEntity, OnHiuRequestBody.class);
    }

    @PostMapping("/getRequestedData")
    public String storeRequestedData(@RequestBody Ehr ehrData)
    {
        ehrRepo.save(ehrData);
        return "data saved";
    }

    @PostMapping("/hip/sendRequest")
    public String hipSendRequest(@RequestBody HipRequestBody hipRequestBody){
        System.out.println("here i am ");
        // check if consentObj is valid in gateway
        // call /gateway/request/verifyRequest
        String url = "http://localhost:9005/gateway/request/verifyConsent";
        VerifyConsentBody verifyConsentBody = new VerifyConsentBody(hipRequestBody.consentObj.hipSSID,
                hipRequestBody.consentObj);
        HttpEntity<VerifyConsentBody> consentEntity = new HttpEntity<>(verifyConsentBody);
        System.out.println(hipRequestBody.consentObj.patientSSID);
        System.out.println(hipRequestBody.consentObj.dataAccessStartDate);
        System.out.println(hipRequestBody.consentObj.dataAccessEndDate);
        ResponseEntity<VerifyConsentResponse> vc_re = this.restTemplate.postForEntity(url, consentEntity,
                VerifyConsentResponse.class);


        // if verified send data
        if(Objects.equals(Objects.requireNonNull(vc_re.getBody()).response, "Verified")){
             url= hipRequestBody.dataPostUrl();

            List<Ehr> ehrData=ehrRepo.findByPatientSsIDAndCreationDateBetween(hipRequestBody.consentObj.patientSSID,LocalDate.parse(hipRequestBody.consentObj.dataAccessStartDate),LocalDate.parse(hipRequestBody.consentObj.dataAccessEndDate));
            System.out.println(ehrData.size());
            System.out.println(hipRequestBody.consentObj.patientSSID);
            System.out.println(hipRequestBody.consentObj.dataAccessStartDate);
            System.out.println(hipRequestBody.consentObj.dataAccessEndDate);
         //   ehrData=ehrRepo
            List<SendRequestedData> data=new ArrayList<>();

            for (Ehr ehrDatum : ehrData) {
//                SendRequestedData sendRequestedData = new SendRequestedData(ehrDatum.getData(), ehrDatum.getPatient().getSsID());
                SendRequestedData sendRequestedData = new SendRequestedData(
                        ehrDatum.getCreationDate(),
                        ehrDatum.getPatient().getSsID(),
                        ehrDatum.getType(),
                        ehrDatum.getObservationCode(),
                        ehrDatum.getObservationValue(),
                        ehrDatum.getConditionCode(),
                        ehrDatum.getProcedureCode());
                data.add(sendRequestedData);
                System.out.println(ehrDatum.getData());
            }

            System.out.println(ehrData.get(0).getPatient().getSsID());

            ResponseEntity<String> response=this.restTemplate.postForEntity(url,data,String.class);
            System.out.println(response.getBody());
        }
        else{
            // notify error

        }
        return "data sent";
    }

}