package com.swaksha.hospitalservice.requests;

import com.swaksha.hospitalservice.entity.Ehr;
import com.swaksha.hospitalservice.entity.Patient;
import com.swaksha.hospitalservice.repository.EhrRepo;
import com.swaksha.hospitalservice.repository.PatientRepo;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
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

//    private final PatientRepo patientRepo;
//
//    private final EhrRepo ehrRepo;
    public record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                             LocalDate dataAccessStartDate, LocalDate dataAccessEndDate,
                             LocalDate requestInitiatedDate, LocalDate consentApprovedDate,
                             LocalDate consentEndDate, String consentID, boolean selfConsent, boolean isApproved) {
    }


//    record EhrData(String data,String patientSSID){}
    record EhrData(LocalDate creationDate, String patientSSID,
                   String type, String observationCode, String observationValue,
                   String conditionCode, String procedureCode){
    }
    record HiuPlaceRequestWithConsent(String docSSID, String patientSSID, String consentID){}

    record HiuRequestBody(String hipSSID,String patientSSID,String doctorSSID) {}

    record HiuRequestWithConsent(String docSSID, String hiuSSID, String patientSSID, ConsentObj consentObj,
                                 String dataPostUrl){}

    record OnHiuRequestBody(String response, String docSSID, String hiuSSID){}


    record HipRequestBody(ConsentObj consentObj, String dataPostUrl){}

    record VerifyConsentResponse(String response){}

    record VerifyConsentBody(String reqSSID, ConsentObj consentObj){}

    record HiuRequestWithConsentId(String consentId){}
    record OnVerifyConsentBody(String response, String reqSSID, ConsentObj consentObj){}

    @PostMapping("/demo")
    public String demo(){
        return "hello !";
    }
    @PostMapping("/newRequest")
    public HttpEntity<OnHiuRequestBody> hiuRequest(@RequestBody HiuRequestBody hiuPlaceRequest, Authentication authentication) {


        String ssid= authentication.getName();
//        System.out.println(hiuPlaceRequest.docSSID);
//        System.out.println(hiuPlaceRequest.patientSSID);
        // call /gateway/request/hiu/request
        String url = "http://localhost:9005/gateway/request/hiu/request";

        System.out.println(hiuPlaceRequest.hipSSID);
        HttpHeaders headers=new HttpHeaders();
        headers.set("swaksha-api-key", "968d36d5-05d9-4ae8-a408-a2803dfb710d");

        HiuRequestBody newHiuRequestBody=new HiuRequestBody(hiuPlaceRequest.hipSSID,hiuPlaceRequest.patientSSID,ssid);
        HttpEntity<HiuRequestBody> reqEntity = new HttpEntity<>(newHiuRequestBody,headers);
        //set API key in header .

        ResponseEntity<OnHiuRequestBody> ohr = this.restTemplate.postForEntity(url, reqEntity, OnHiuRequestBody.class);

        return new ResponseEntity<>(ohr.getBody(), HttpStatus.OK);
    }

    @PostMapping("/requestWithConsent")
    public void hiuRequestWithConsentBody(@RequestBody HiuRequestWithConsentId hiuRequestWithConsentId){

        // call /gateway/request/hiu/requestWithConsent
        String url = "http://localhost:9005/gateway/request/hiu/requestWithConsent";


        HttpHeaders headers=new HttpHeaders();
        headers.set("swaksha-api-key", "968d36d5-05d9-4ae8-a408-a2803dfb710d");

        HttpEntity<HiuRequestWithConsentId> reqEntity =
                new HttpEntity<>(hiuRequestWithConsentId,headers);

        ResponseEntity<OnHiuRequestBody> ohr = this.restTemplate.postForEntity(url, reqEntity, OnHiuRequestBody.class);
    }

    @PostMapping("/getRequestedData")
    public String storeRequestedData(@RequestBody List<EhrData> ehrData)
    {
        Patient patient=this.requestService.findPatientById(ehrData.get(0).patientSSID);
        for(int i=0;i<ehrData.size();i++){
            System.out.println(ehrData.get(i).patientSSID);
//            System.out.println(ehrData.get(i).data);
            Ehr ehr=new Ehr();
//            ehr.setData(ehrData.get(i).data);
            ehr.setCreationDate(ehrData.get(i).creationDate);
            ehr.setType(ehrData.get(i).type);
            ehr.setObservationCode(ehrData.get(i).observationCode);
            ehr.setObservationValue(ehrData.get(i).observationValue);
            ehr.setConditionCode(ehrData.get(i).conditionCode);
            ehr.setProcedureCode(ehrData.get(i).procedureCode);

            ehr.setPatient(patient);
            this.requestService.save(ehr);
        }


    //    Ehr.builder().data(ehrData.getData()).patient(ehrData.getPatient());


        return "data saved";
    }

    @PostMapping("/hip/sendRequest")
    public void hipSendRequest(@RequestBody HipRequestBody hipRequestBody){

        // check if consentObj is valid in gateway
        // call /gateway/request/verifyRequest
        String url = "http://localhost:8999/gateway/request/verifyConsent";
        VerifyConsentBody verifyConsentBody = new VerifyConsentBody(hipRequestBody.consentObj.hipSSID,
                hipRequestBody.consentObj);
        HttpEntity<VerifyConsentBody> consentEntity = new HttpEntity<>(verifyConsentBody);
        ResponseEntity<VerifyConsentResponse> vc_re = this.restTemplate.postForEntity(url, consentEntity,
                VerifyConsentResponse.class);

        // if verified send data
        if(Objects.equals(Objects.requireNonNull(vc_re.getBody()).response, "Verified")){
            url= hipRequestBody.dataPostUrl();
            ArrayList<Ehr> ehrData = this.requestService.fetchEhrData(hipRequestBody.consentObj.patientSSID);

            ResponseEntity<String> response=this.restTemplate.postForEntity(url,ehrData,String.class);
            System.out.println(response.getBody());
        }
        else{
            // notify error
        }
    }

    @PostMapping("/storeEHR")
    public void storeEHR () {}

    @PostMapping("/consentUpdate")
    public HttpEntity<Boolean> consentUpdate(@RequestBody ConsentObj consentObj){
        boolean update = this.requestService.updateConsentObj(consentObj);
        return new HttpEntity<>(update);
    }

}