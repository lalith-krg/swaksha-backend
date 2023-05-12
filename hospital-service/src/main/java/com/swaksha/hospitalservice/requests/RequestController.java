package com.swaksha.hospitalservice.requests;

import com.swaksha.hospitalservice.entity.Ehr;
import com.swaksha.hospitalservice.entity.Patient;
import com.swaksha.hospitalservice.repository.ConsentRepo;
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

    private final ConsentRepo consentRepo;
    private final PatientRepo patientRepo;
//
    private final EhrRepo ehrRepo;
    public record ConsentObj(String doctorSSID, String hiuSSID, String patientSSID, String hipSSID,
                             LocalDate dataAccessStartDate, LocalDate dataAccessEndDate,
                             LocalDate requestInitiatedDate, LocalDate consentApprovedDate,
                             LocalDate consentEndDate, String consentID, boolean selfConsent, boolean isApproved) {
    }


//    record EhrData(String data,String patientSSID){}
    record EhrData(LocalDate creationDate, String patientSSID, String type, String observationCode,
                   String observationValue, String conditionCode, String procedureCode){
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
    record NToken(String token){}
    record NToken2(String ssid, String token){}

    record FetchConsents(ArrayList<ConsentObj> consentObjs){};
    @PostMapping("/demo")
    public String demo(){
        return "hello !";
    }

    @PostMapping("/fetchConsents")
    public HttpEntity<FetchConsents> fetchConsents(@RequestBody String consentId,Authentication authentication){
        String docSsid=authentication.getName();
        ArrayList<ConsentObj> consentObjs=this.requestService.findByDoctorSsid(docSsid);
        FetchConsents fetchConsents=new FetchConsents(consentObjs);
        HttpEntity<FetchConsents> fetchConsentsHttpEntity=new HttpEntity<>(fetchConsents);
        return fetchConsentsHttpEntity;
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
        headers.set("swaksha-api-key", "1a6da3dd-1b83-4791-a8ba-70ccfe25aa7d");

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
        headers.set("swaksha-api-key", "1a6da3dd-1b83-4791-a8ba-70ccfe25aa7d");

        HttpEntity<HiuRequestWithConsentId> reqEntity =
                new HttpEntity<>(hiuRequestWithConsentId,headers);

        ResponseEntity<OnHiuRequestBody> ohr = this.restTemplate.postForEntity(url, reqEntity, OnHiuRequestBody.class);
    }

    @PostMapping("/getRequestedData")
    public String storeRequestedData(@RequestBody ArrayList<EhrData> ehrData)
    {



        if (ehrData.size()<1){
            System.out.println("Empty data.");
            return "Empty data";
        }

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

        // notification-to-patient-({number of recordds} fetched)-(From HIP to HIU)

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
    public HttpEntity<String> storeEHR (@RequestBody EhrData ehrRecord, Authentication authentication) {
//        System.out.println("reached here");
//        System.out.println(ehrRecord.type);
//        System.out.println(ehrRecord.observationCode);
//        System.out.println(ehrRecord.observationValue);
//        System.out.println(ehrRecord.conditionCode);
//        System.out.println(ehrRecord.creationDate);
//        System.out.println(ehrRecord.procedureCode);
//        System.out.println(ehrRecord.patientSSID);

        Patient patient = patientRepo.findPatientBySsid(ehrRecord.patientSSID);

        if (patient == null) {
            return new ResponseEntity<>("notfound", HttpStatus.OK);
        }

        Ehr ehr = new Ehr();
        ehr.setType(ehrRecord.type);
        ehr.setCreationDate(ehrRecord.creationDate);
        ehr.setObservationCode(ehrRecord.observationCode);
        ehr.setObservationValue(ehrRecord.observationValue);
        ehr.setConditionCode(ehrRecord.conditionCode);
        ehr.setProcedureCode(ehrRecord.procedureCode);
        ehr.setPatient(patient);

        ehrRepo.save(ehr);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    @PostMapping("/consentUpdate")
    public HttpEntity<Boolean> consentUpdate(@RequestBody ConsentObj consentObj){
        boolean update = this.requestService.updateConsentObj(consentObj);
        return new HttpEntity<>(update);
    }

    @PostMapping("/deleteConsent")
    public HttpEntity<Boolean> deleteConsent(@RequestBody String consentId){
        boolean update = this.requestService.deleteConsentObj(consentId);
        return new HttpEntity<>(update);
    }

    @PostMapping("/assign-notification-token")
    public Boolean assignNotificationToken(@RequestBody NToken ntoken, Authentication authentication){
        String ssid= authentication.getName();
       String url = "http://localhost:9005/api/v1/auth/assign-notification-token-hospital";

       HttpHeaders headers=new HttpHeaders();
       headers.set("swaksha-api-key", "968d36d5-05d9-4ae8-a408-a2803dfb710d");
       HttpEntity<NToken2> reqEntity = new HttpEntity<>(new NToken2(ssid, ntoken.token), headers);

       ResponseEntity<Boolean> ohr = this.restTemplate.postForEntity(url, reqEntity, Boolean.class);

        return true;
    }

}